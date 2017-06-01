package com.zhenhappy.ems.manager.action.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.PDFOperator;
import org.apache.pdfbox.util.PDFTextStripper;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * Created by Administrator on 2016/11/16.
 */
public class PDFReaderUtil {
    @Autowired
    FreeMarkerConfigurer freeMarker;// 注入FreeMarker模版封装框架

    public static final String exhibitorInvitationInputPath = "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\Booth Confirmation of Xiamen Stone Fair 2017.pdf";
    public static final String exhibitorInvitationSavePath = "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\boothconfirm\\";
    public static final String exhibitorConfirmFilePath = "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\Booth Setup Authorization of Xiamen Stone Fair 2017.pdf";
    public static final String exhibitorSelectPath = "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\select.png";
    public PDFReaderUtil()
    {
        //createHelloPDF();
        //readPDF();
        /*try {
            //doIt("D:\\pdfwithText.pdf", "D:\\helloworld.pdf", "Hello", "try it again");
            //editPdf("D:\\\\pdfwithText.pdf", "D:\\\\helloworld.pdf", "丰镇市晶磊贸易有限公司", "D:\\\\select.png", "C3L33-C3L34");
            writePDF(exhibitorInvitationInputPath,
                    exhibitorInvitationSavePath + "丰镇市晶磊贸易有限公司" + ".pdf", "丰镇市晶磊贸易有限公司",
                    "32405364-3", "32405364-3",
                    exhibitorSelectPath, "C3L33-C3L34", "80", "1");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }*/
    }

    public void writePDF(String inputFile, String outputFile, String companyName, String account, String password,
                         String selectImage, String boothNum, String areaNum, String flag) throws IOException, DocumentException {
        //创建一个pdf读入流
        PdfReader reader = new PdfReader(inputFile);
        //根据一个pdfreader创建一个pdfStamper.用来生成新的pdf.
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));

        //这个字体是itext-asian.jar中自带的 所以不用考虑操作系统环境问题.
        // BaseFont bf = BaseFont.createFont("Identity-H", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED); // set font
        //BaseFont.addToResourceSearch("iTextAsianCmaps.dll"); //"STSong-Light", "UniGB-UCS2-H",
        BaseFont bf = BaseFont.createFont("c://windows//fonts//simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        //baseFont不支持字体样式设定.但是font字体要求操作系统支持此字体会带来移植问题.
        Font font = new Font(bf,20);
        font.setStyle(Font.BOLD);
        font.getBaseFont();
        //页数是从1开始的
        for (int i=1; i<=reader.getNumberOfPages(); i++){
            //获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上.
            PdfContentByte over = stamper.getOverContent(i);
            //用pdfreader获得当前页字典对象.包含了该页的一些数据.比如该页的坐标轴信息.
            PdfDictionary p = reader.getPageN(i);
            //拿到mediaBox 里面放着该页pdf的大小信息.
            PdfObject po =  p.get(new PdfName("MediaBox"));
            //System.out.println(po.isArray());
            //po是一个数组对象.里面包含了该页pdf的坐标轴范围.
            PdfArray pa = (PdfArray) po;
            //System.out.println(pa.size());
            //看看y轴的最大值.
            //System.out.println(pa.getAsNumber(pa.size()-1));
            //开始写入文本
            over.beginText();
            //设置字体和大小
            over.setFontAndSize(font.getBaseFont(), 13);
            over.setColorFill(new java.awt.Color(255,0,0));
            if(i==1){
                //设置字体的输出位置
                //输出公司名
                over.setTextMatrix(170, 650);
                over.showText(companyName);

                //创建一个image对象.
                Image image = Image.getInstance(selectImage);
                //设置image对象的输出位置pa.getAsNumber(pa.size()-1).floatValue() 是该页pdf坐标轴的y轴的最大值
                //image.setAbsolutePosition(0,pa.getAsNumber(pa.size()-1).floatValue()-100);//0, 0, 841.92, 595.32
                if("1".equals(flag)){
                    //标摊选择图片
                    image.setAbsolutePosition(145,610);
                    over.addImage(image);
                }else{
                    //空地选择图片
                    image.setAbsolutePosition(84,610);
                    over.addImage(image);
                }

                //输出展位号
                over.setTextMatrix(275, 620);
                over.showText(boothNum);

                //输出平方数
                over.setTextMatrix(435, 620);
                over.showText(areaNum + "㎡");
            }
            if(i==2){
                //设置字体的输出位置
                //输出账号
                over.setTextMatrix(190, 460);
                over.showText(account);

                //输出密码
                over.setTextMatrix(380, 460);
                over.showText(password);
            }

            over.endText();

            /*//画一个圈.
            over.setRGBColorStroke(0xFF, 0x00, 0x00);
            over.setLineWidth(5f);
            over.ellipse(250, 450, 350, 550);
            over.stroke();*/
        }
        stamper.close();
    }

    public void createHelloPDF() {
        PDDocument doc = null;
        PDPage page = null;

        try {
            doc = new PDDocument();
            page = new PDPage();

            doc.addPage(page);
            PDFont font = PDType1Font.HELVETICA_BOLD;
            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.beginText();
            content.setFont(font, 22);
            content.moveTextPositionByAmount(100, 700);
            content.drawString("Hello");

            content.endText();
            content.close();
            doc.save("D:\\pdfwithText.pdf");
            doc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void readPDF()
    {
        PDDocument helloDocument;
        try {
            helloDocument = PDDocument.load(new File("D:\\pdfwithText.pdf"));
            PDFTextStripper textStripper = new PDFTextStripper();
            System.out.println(textStripper.getText(helloDocument));
            helloDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doIt( String inputFile, String outputFile, String strToFind, String message) throws IOException, COSVisitorException
    {
        // the document
        PDDocument doc = null;
        try
        {
            doc = PDDocument.load( inputFile );
            //PDFTextStripper stripper=new PDFTextStripper("ISO-8859-1");
            List pages = doc.getDocumentCatalog().getAllPages();
            for( int i=0; i<pages.size(); i++ )
            {
                PDPage page = (PDPage)pages.get( i );
                PDStream contents = page.getContents();
                PDFStreamParser parser = new PDFStreamParser(contents.getStream() );
                parser.parse();
                List tokens = parser.getTokens();
                for( int j=0; j<tokens.size(); j++ )
                {
                    Object next = tokens.get( j );
                    if( next instanceof PDFOperator )
                    {
                        PDFOperator op = (PDFOperator)next;
                        //Tj and TJ are the two operators that display
                        // strings in a PDF
                        if( op.getOperation().equals( "Tj" ) )
                        {
                            //Tj takes one operator and that is the string
                            // to display so lets update that operator
                            COSString previous = (COSString)tokens.get( j-1 );
                            String string = previous.getString();
                            string = string.replaceFirst( strToFind, message );
                            System.out.println(string);
                            System.out.println(string.getBytes("UTF-8"));
                            previous.reset();
                            previous.append( string.getBytes("UTF-8") );
                        }
                        else if( op.getOperation().equals( "TJ" ) )
                        {
                            COSArray previous = (COSArray)tokens.get( j-1 );
                            for( int k=0; k<previous.size(); k++ )
                            {
                                Object arrElement = previous.getObject( k );
                                if( arrElement instanceof COSString )
                                {
                                    COSString cosString = (COSString)arrElement;
                                    String string = cosString.getString();
                                    string = string.replaceFirst( strToFind, message );
                                    cosString.reset();
                                    cosString.append( string.getBytes("UTF-8") );
                                }
                            }
                        }
                    }
                }
                //now that the tokens are updated we will replace the
                // page content stream.
                PDStream updatedStream = new PDStream(doc);
                OutputStream out = updatedStream.createOutputStream();
                ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
                tokenWriter.writeTokens( tokens );
                page.setContents( updatedStream );
            }
            doc.save( outputFile );
        }
        finally
        {
            if( doc != null )
            {
                doc.close();
            }
        }
    }

    public void editPDF() {
        try {
            PDDocument helloDocument = PDDocument.load(new File("mail/Booth Confirmation of Xiamen Stone Fair 2017.pdf"));
            // PDDocument helloDocument = PDDocument.load(new File("D:\\gloomyfish\\hello.pdf"));
            // int pageCount = helloDocument.getNumberOfPages();
            PDPage firstPage = (PDPage)helloDocument.getDocumentCatalog().getAllPages().get(0);
            // PDPageContentStream content = new PDPageContentStream(helloDocument, firstPage);
            PDStream contents = firstPage.getContents();

            PDFStreamParser parser = new PDFStreamParser(contents.getStream());
            parser.parse();
            List tokens = parser.getTokens();
            for (int j = 0; j < tokens.size(); j++)
            {
                Object next = tokens.get(j);
                if (next instanceof PDFOperator)
                {
                    PDFOperator op = (PDFOperator) next;
                    // Tj and TJ are the two operators that display strings in a PDF
                    if (op.getOperation().equals("Tj"))
                    {
                        // Tj takes one operator and that is the string
                        // to display so lets update that operator
                        COSString previous = (COSString) tokens.get(j - 1);
                        String string = previous.getString();
                        string = string.replaceFirst("Hello", "展商邀请涵");
                        //Word you want to change. Currently this code changes word "Solr" to "Solr123"
                        previous.reset();
                        previous.append(string.getBytes("ISO-8859-1"));
                    }
                    else if (op.getOperation().equals("TJ"))
                    {
                        COSArray previous = (COSArray) tokens.get(j - 1);
                        for (int k = 0; k < previous.size(); k++)
                        {
                            Object arrElement = previous.getObject(k);
                            if (arrElement instanceof COSString)
                            {
                                COSString cosString = (COSString) arrElement;
                                String string = cosString.getString();
                                string = string.replaceFirst("Hello", "展商邀请涵");

                                // Currently this code changes word "Solr" to "Solr123"
                                cosString.reset();
                                cosString.append(string.getBytes("ISO-8859-1"));
                            }
                        }
                    }
                }
            }
            // now that the tokens are updated we will replace the page content stream.
            PDStream updatedStream = new PDStream(helloDocument);
            OutputStream out = updatedStream.createOutputStream();
            ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
            tokenWriter.writeTokens(tokens);
            firstPage.setContents(updatedStream);
            helloDocument.save("D:\\helloworld.pdf"); //Output file name
            helloDocument.close();
//          PDFTextStripper textStripper = new PDFTextStripper();
//          System.out.println(textStripper.getText(helloDocument));
//          helloDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (COSVisitorException e) {
            e.printStackTrace();
        }
    }

    public String getExhibitorInvisitorMailText() throws Exception {
        // 通过指定模板名获取FreeMarker模板实例
        Template template = freeMarker.getConfiguration().getTemplate("mail/email_content");

        // FreeMarker通过Map传递动态数据
        Map<Object, Object> model = new HashMap<Object, Object>();

        // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
        String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        return htmlText;
    }

    public static void main(String[] args) {
        new PDFReaderUtil();
    }
}
