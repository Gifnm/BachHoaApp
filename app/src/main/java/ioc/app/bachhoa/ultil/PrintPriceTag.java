package ioc.app.bachhoa.ultil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.Toast;

import com.dantsu.escposprinter.EscPosCharsetEncoding;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.EscPosPrinterCommands;
import com.dantsu.escposprinter.EscPosPrinterSize;
import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.textparser.PrinterTextParser;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.github.danielfelgar.drawreceiptlib.ReceiptBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;

public class PrintPriceTag {
    private Context context;

    public PrintPriceTag(Context context) {
        this.context = context;
    }

    public PrintPriceTag() {
    }

    private void printPriceTags() {

    }

    public void printOnePriceTag() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    EscPosPrinter printer = new EscPosPrinter(new TcpConnection("192.168.1.8", 9100, 1000), 203, 48f, 32);
//                    printer.
//                            printFormattedText(
//                                    "[L]\n" +
//                                            "[C]<u><font>Ke 1 Mam 1 Vi tri 4 Trung 2</font></u>\n" +
//                                            "[C]<u><font>Sữa Vinamilk ô </font></u>\n" +
//                                            "[C]<barcode type='ean13' height='10'>8312547845512</barcode>\n"+
//                                    "[C]<u><font size='big'>4.500</font></u>\n"
//
//
//                            );
                    String text = "Nguyễn Minh Thư";

                    int targetWidth = 383; // 48mm printing zone with 203dpi => 383px

// Tạo một Bitmap với nội dung "Xin chào"
                    // Tạo một hình ảnh Bitmap với kích thước cố định (ví dụ: 300x400 pixels)
                    int width = 300;
                    int height = 400;
                    Bitmap.Config config = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                    Canvas canvas = new Canvas(bitmap);

// Vẽ nền trắng
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    canvas.drawRect(0, 0, 500, 900, paint);

// Vẽ tiêu đề hóa đơn
                    Paint titlePaint = new Paint();
                    titlePaint.setColor(Color.BLACK);
                    titlePaint.setTextSize(24);
                    canvas.drawText("HÓA ĐƠN BÁN HÀNG", 50, 30, titlePaint);

// Vẽ thông tin khách hàng
                    Paint infoPaint = new Paint();
                    infoPaint.setColor(Color.BLACK);
                    infoPaint.setTextSize(16);
                    canvas.drawText("Tên khách hàng: Nguyễn Minh Thư", 50, 70, infoPaint);
                    canvas.drawText("Địa chỉ: 123 Main St, City", 50, 90, infoPaint);

// Vẽ danh sách các mục hàng
                    Paint itemPaint = new Paint();
                    itemPaint.setColor(Color.BLACK);
                    itemPaint.setTextSize(16);
                    canvas.drawText("Danh sách sản phẩm:", 50, 130, itemPaint);
                    canvas.drawText("- Sản phẩm 1: $10.00", 50, 150, itemPaint);
                    canvas.drawText("- Sản phẩm 2: $20.00", 50, 170, itemPaint);
                    canvas.drawText("- Sản phẩm 3: $15.00", 50, 190, itemPaint);

// Vẽ tổng cộng
                    Paint totalPaint = new Paint();
                    totalPaint.setColor(Color.BLACK);
                    totalPaint.setTextSize(20);
                    canvas.drawText("Tổng cộng: $45.00", 50, 240, totalPaint);

// Hiển thị hoặc lưu Bitmap


// Đảm bảo Bitmap có kích thước phù hợp
                    Bitmap rescaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, Math.round(((float) bitmap.getHeight()) * ((float) targetWidth) / ((float) bitmap.getWidth())), true);
//                    PrinterTextParserImg.bitmapToHexadecimalString(printer, hi(), false);
                    // printer.printFormattedText("[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, hi(), false) + "</img>\n");
// Bổ sung vào mã của bạn
                    EscPosPrinterCommands printerCommands = new EscPosPrinterCommands(new TcpConnection("192.168.1.8", 9100, 1000));
                    try {

                        printerCommands.connect();
                        printerCommands.reset();
                        printerCommands.printImage(EscPosPrinterCommands.bitmapToBytes(generateOnePriceTag(), false));

                        printerCommands.feedPaper(50);
                        printerCommands.cutPaper();
                    } catch (EscPosConnectionException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public Bitmap byteArrayToBitmap() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        Paragraph paragraph = new Paragraph("hello Nguyễn Minh Thư, Chào bạn của tôihello Nguyễn Minh Thư, Chào bạn của tôihello Nguyễn Minh Thư, Chào bạn của tôivvvvvhello Nguyễn Minh Thư, Chào bạn của tôihello Nguyễn Minh Thư, Chào bạn của tôivvhello Nguyễn Minh Thư, Chào bạn của tôi \n hello Nguyễn Minh Thư, Chào bạn của tôihello Nguyễn Minh Thư, Chào bạn của tôivvvhello Nguyễn Minh Thư, Chào bạn của tôihello Nguyễn Minh Thư, Chào bạn của tôiv\nhello Nguyễn Minh Thư, Chào bạn của tôihello Nguyễn Minh Thư, Chào bạn của tôivvvvhello Nguyễn Minh Thư, Chào bạn của tôihello Nguyễn Minh Thư, Chào bạn của tôi");
        document.add(paragraph);
        document.close();

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    private void createBitmap1() {


    }

    private void printShelfTag() {
    }

    private void allPriceOnShelf() {
    }

    private void allPriceOnPlatter() {

    }

    private void printIs(EscPosPrinterCommands printer, Bitmap bitmap) throws EscPosConnectionException {


        Bitmap toPrint = rescale(printer, bitmap);

        byte[] bytesPicture = EscPosPrinterCommands.bitmapToBytes(toPrint, false);
        byte[] bytesCut = new byte[]{0x1D, 0x56, 0x01}; //The command for cut
        byte[] combined = new byte[bytesPicture.length + bytesCut.length];

        System.arraycopy(bytesPicture, 0, combined, 0, bytesPicture.length);
        System.arraycopy(bytesCut, 0, combined, bytesPicture.length, bytesCut.length);

        printer.printImage(combined);
//Print the image and cut after without delay
    }

    public Bitmap rescale(EscPosPrinterCommands printer, Bitmap bitmap) {
        int maxWidth = 586;
        Bitmap newBitmap = bitmap;
        if (bitmap.getWidth() > maxWidth) {
            double ratio = Double.valueOf(bitmap.getHeight()) / Double.valueOf(bitmap.getWidth());
            int maxHeight = (int) (Math.ceil(maxWidth * ratio));
            newBitmap = Bitmap.createScaledBitmap(bitmap, maxWidth, maxHeight, false);
        }
        return newBitmap;
    }

    public Bitmap hi() {

        ReceiptBuilder receipt = new ReceiptBuilder(383);
        receipt.
                setAlign(Paint.Align.CENTER).
                setColor(Color.BLACK).
                setTextSize(30).
                addText("Kệ 1 Mâm 2 vị trí 3 trưng 2").

                addText("26.500").
                addText("1234 Main St.").
                addText("Palo Alto, CA 94568").
                addText("999-999-9999").
                setAlign(Paint.Align.LEFT).
                addText("Terminal ID: 123456", false).
                setAlign(Paint.Align.RIGHT).
                addText("1234").
                setAlign(Paint.Align.LEFT).
                addLine().
                addText("08/15/16", false).
                setAlign(Paint.Align.RIGHT).
                addText("SERVER #4").
                setAlign(Paint.Align.LEFT).
                addParagraph().
                addText("CHASE VISA - INSERT").
                addText("AID: A000000000011111").
                addText("ACCT #: *********1111").
                addParagraph().

                addText("CREDIT SALE").
                addText("UID: 12345678", false).
                setAlign(Paint.Align.RIGHT).
                addText("REF #: 1234").

                setAlign(Paint.Align.LEFT).
                addText("BATCH #: 091", false).
                setAlign(Paint.Align.RIGHT).
                addText("AUTH #: 0701C").
                setAlign(Paint.Align.LEFT).
                addText("AMOUNT", false).
                setAlign(Paint.Align.RIGHT).
                addText("$ 15.00").
                setAlign(Paint.Align.LEFT).
                addParagraph().
                addText("TIP", false).
                setAlign(Paint.Align.RIGHT).
                addText("$        ").
                setAlign(Paint.Align.LEFT).
                addParagraph().
                addText("TOTAL", false).
                setAlign(Paint.Align.RIGHT).
                addText("$        ").
                addParagraph().
                setAlign(Paint.Align.CENTER).
                addText("APPROVED").addLine().setTextSize(35).addText("Size35")
                .addImage(generateBarcode("01234567890123", 250, 70));

        return receipt.build();
    }

    public void printBill() {
        new Thread(new Runnable() {
            public void run() {
                EscPosPrinterCommands printerCommands = new EscPosPrinterCommands(new TcpConnection("192.168.1.8", 9100, 1000));
                try {

                    printerCommands.connect();
                    printerCommands.reset();
                    printerCommands.printImage(EscPosPrinterCommands.bitmapToBytes(generateBill(), false));
                    printerCommands.feedPaper(50);
                    printerCommands.cutPaper();
                } catch (EscPosConnectionException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    public void printOnetag() {

        EscPosPrinterCommands printerCommands = new EscPosPrinterCommands(new TcpConnection("192.168.1.8", 9100, 1000));
        try {

            printerCommands.connect();
            printerCommands.reset();
            printerCommands.printImage(EscPosPrinterCommands.bitmapToBytes(generateBarcode("1234567890123", 250, 70), false));

            printerCommands.feedPaper(50);
            printerCommands.cutPaper();
        } catch (EscPosConnectionException e) {
            e.printStackTrace();
        }
    }

    private Bitmap generateOnePriceTag() {
        ReceiptBuilder receipt = new ReceiptBuilder(383);
        receipt.
                setAlign(Paint.Align.CENTER).
                setColor(Color.BLACK).
                setTextSize(28).
                addText("Kệ 1 Mâm 2 vị trí 3 trưng 2").setTextSize(25).addLine()
                .addText("Sữa Vinamilk có đường gói 350ml")
                .addImage(generateBarcode("01234567890123", 250, 70)).addParagraph()
                .setTextSize(40)
                .addText("26.500 VND").addParagraph();
        return receipt.build();
    }

    public Bitmap generateBill() {


        ReceiptBuilder receipt = new ReceiptBuilder(383);
        receipt.
                setAlign(Paint.Align.CENTER).
                setColor(Color.BLACK).
                setTextSize(27).
                addText("BachHoa").
                setTextSize(25).
                addText("BachHoa.com").
                addLine().setTextSize(27).addText("Phiếu Thanh Toán").addParagraph().
                setAlign(Paint.Align.LEFT).
                addText("Số CT: " + System.currentTimeMillis()).
                addText("Ngày CT: 1/11/2023 7:24").
                addText("Nhân viên: Nguyễn Minh Thư").addParagraph().addLine().addText("Số lượng",false).setAlign(Paint.Align.CENTER)
                .addText("Giá bán",false).setAlign(Paint.Align.RIGHT).addText("Thành tiền").addText(" ")
                .setAlign(Paint.Align.LEFT).addText("Doanh nghiệp của thế kí 21").addText(" ").setAlign(Paint.Align.LEFT)
                .addText("1",false)
                .setAlign(Paint.Align.CENTER).addText("145.000",false)
                .setAlign(Paint.Align.RIGHT).addText("145.000")
                .addParagraph().addLine().setAlign(Paint.Align.LEFT).addText("Ví: 0")
                .addText("Tổng tiền: 145.000 VND")
                .addText("Tiền mặt: 145.000 VND")
                .addText("Tiền thối: 0 VND")
                .addText(" ")
                .addLine()
                .addText(" ")
                .setAlign(Paint.Align.CENTER)
                .addText("Mã hóa đơn/ Hóa đơn điện tử")
                .addImage(generateBarcode("1234567890123",300,70))
                .addParagraph()
                .addLine().addText(" ")
                .addText(
                        "Cảm ơn quý khách!");
        return receipt.build();

    }
    public Bitmap SharedBillPNG() {


        ReceiptBuilder receipt = new ReceiptBuilder(1200);
        receipt.setMarginTop(40).setMarginLeft(15).setMarginRight(15).setMarginBottom(30).
                setAlign(Paint.Align.CENTER).
                setColor(Color.BLACK).
                setTextSize(60).
                addText("BachHoa").
                setTextSize(50).
                addText("BachHoa.com").
                addLine().setTextSize(60).addText("Phiếu Thanh Toán").addParagraph().
                setAlign(Paint.Align.LEFT).
                addText("Số CT: " + System.currentTimeMillis()).
                addText("Ngày CT: 1/11/2023 7:24").
                addText("Nhân viên: Nguyễn Minh Thư").addParagraph().addLine().addText("Số lượng",false).setAlign(Paint.Align.CENTER)
                .addText("Giá bán",false).setAlign(Paint.Align.RIGHT).addText("Thành tiền").addText(" ")
                .setAlign(Paint.Align.LEFT).addText("Doanh nghiệp của thế kí 21").addText(" ").setAlign(Paint.Align.LEFT)
                .addText("1",false)
                .setAlign(Paint.Align.CENTER).addText("145.000",false)
                .setAlign(Paint.Align.RIGHT).addText("145.000").addText(" ")
                .setAlign(Paint.Align.LEFT).addText("Doanh nghiệp của thế kí 21").addText(" ").setAlign(Paint.Align.LEFT)
                .addText("1",false)
                .setAlign(Paint.Align.CENTER).addText("145.000",false)
                .setAlign(Paint.Align.RIGHT).addText("145.000")

                .addParagraph().addLine().setAlign(Paint.Align.LEFT).addText("Ví: 0")
                .addText("Tổng tiền: 145.000 VND")
                .addText("Tiền mặt: 145.000 VND")
                .addText("Tiền thối: 0 VND")
                .addText(" ")
                .addLine()
                .addText(" ")
                .setAlign(Paint.Align.CENTER)
                .addText("Mã hóa đơn/ Hóa đơn điện tử")
                .addImage(generateBarcode("1234567890123",600,140))
                .addParagraph()
                .addLine().addText(" ")
                .addText(
                        "Cảm ơn quý khách!");
        return receipt.build();

    }

    private Bitmap generateBarcode(String barcode, int width, int height) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(barcode, BarcodeFormat.CODE_128, width, height);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (Exception e) {
            Log.e("MyApp", "StackTrace: " + Log.getStackTraceString(e));
            return null;
        }
    }


}
