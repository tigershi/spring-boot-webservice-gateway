package cn.picc.finance.sample.conf;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;


public class ReqContentLogInInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private static Logger logger = LoggerFactory.getLogger(ReqContentLogInInterceptor.class);

    public ReqContentLogInInterceptor() {
        // 设置拦截的阶段，这里设置为接收阶段
        super(Phase.RECEIVE);
    }


    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        logging(message);
    }

    private void logging(SoapMessage message) throws Fault {


        if (message.containsKey(LoggingMessage.ID_KEY)) {
            return;
        }
        String id = (String) message.getExchange().get(LoggingMessage.ID_KEY);
        if (id == null) {
            id = LoggingMessage.nextId();
            message.getExchange().put(LoggingMessage.ID_KEY, id);
        }
        message.put(LoggingMessage.ID_KEY, id);

        LoggingMessage buffer = new LoggingMessage("Inbound Message\n----------------------------", id);

        String encoding = (String) message.get(Message.ENCODING);

        if (encoding != null) {
            buffer.getEncoding().append(encoding);
        }
        String ct = (String) message.get("Content-Type");
        if (ct != null) {
            buffer.getContentType().append(ct);
        }
        Object headers = message.get(Message.PROTOCOL_HEADERS);

        if (headers != null) {
            buffer.getHeader().append(headers);
        }
        String uri = (String) message.get(Message.REQUEST_URI);
        if (uri != null) {
            buffer.getAddress().append(uri);
        }

        InputStream is = (InputStream) message.getContent(InputStream.class);
        if (is != null) {
            CachedOutputStream bos = new CachedOutputStream();
            try {
                IOUtils.copy(is, bos);
                bos.flush();
                is.close();
                message.setContent(InputStream.class, bos.getInputStream());
                /**
                if (bos.getTempFile() != null) {
                    buffer.getMessage().append("\nMessage (saved to tmp file):\n");
                    buffer.getMessage().append("Filename: " + bos.getTempFile().getAbsolutePath() + "\n");
                }
                if (bos.size() > this.limit) {
                    buffer.getMessage().append("(message truncated to " + this.limit + " bytes)\n");
                }
                 */
                bos.writeCacheTo(buffer.getPayload());

                bos.close();
            } catch (IOException e) {
                throw new Fault(e);
            }
        }
        // 打印日志，保存日志保存这里就可以，可写库或log4j记录日志
        logger.info("input soap xml:\n {}", buffer.toString());
    }



}

