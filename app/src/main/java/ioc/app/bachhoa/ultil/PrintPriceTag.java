package ioc.app.bachhoa.ultil;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.DTOEntity.PriceTag;
import ioc.app.bachhoa.model.Bill;
import ioc.app.bachhoa.model.BillDetail;
import ioc.app.bachhoa.model.ProductPositioning;

public class PrintPriceTag {
    private Context context;
    private int port = 9100;
    private String ipPrinter = "192.168.1.5";
    DecimalFormat decimalFormat = new DecimalFormat("#,###");

    // C
    public PrintPriceTag(Context context) {
        this.context = context;
    }

    /**
     * In hóa đơn
     *
     * @param bitmap Ảnh hóa đơn cần in
     */
    public void printBill(Bitmap bitmap) {
        new Thread(new Runnable() {
            public void run() {
                EscPosPrinterCommands printerCommands = new EscPosPrinterCommands(new TcpConnection("192.168.1.8", 9100, 1000));
                try {
                    printerCommands.connect();
                    printerCommands.reset();
                    printerCommands.printImage(EscPosPrinterCommands.bitmapToBytes(bitmap, false));
                    printerCommands.feedPaper(50);
                    printerCommands.cutPaper();
                } catch (EscPosConnectionException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    /**
     * In 1 tem giá
     * Tham số: Bitmap tem giá
     *
     * @param bitmap Ảnh tem giá
     */
    public void printOnetag(Bitmap bitmap) {
        new Thread(new Runnable() {
            public void run() {
                SharedPreferences sharedPreferences = context.getSharedPreferences("printerip", context.MODE_PRIVATE);
                String ip = sharedPreferences.getString("printerip", "");
                if (!ipPrinter.equals("")) {
                    EscPosPrinterCommands printerCommands = new EscPosPrinterCommands(new TcpConnection(ip, port, 1000));
                    try {
                        printerCommands.connect();
                        printerCommands.reset();
                        printerCommands.printImage(EscPosPrinterCommands
                                .bitmapToBytes(bitmap, false));
//                    printerCommands.feedPaper(50);
//                    printerCommands.cutPaper();
                    } catch (EscPosConnectionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "Hãy chọn máy in!", Toast.LENGTH_SHORT).show();

                }
            }


        }).start();
    }

    /**
     * In nhiều tem giá
     *
     * @param list Danh sách hình ảnh tem giá cần in
     */
    public void printPriceTags(ArrayList<Bitmap> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EscPosPrinterCommands printerCommands = new EscPosPrinterCommands(new TcpConnection(ipPrinter, port, 1000));
                try {
                    printerCommands.connect();

                    printerCommands.reset();
                    for (Bitmap bitmap : list
                    ) {
                        printerCommands.printImage(EscPosPrinterCommands
                                .bitmapToBytes(bitmap, false));
                    }
                    printerCommands.feedPaper(50);
                    printerCommands.cutPaper();
                } catch (EscPosConnectionException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    /**
     * Tạo ảnh 1 tem giá
     *
     * @param priceTag Object Vị trí sản phẩm
     */
    public Bitmap generateOnePriceTag(PriceTag priceTag) {
        ReceiptBuilder receipt = new ReceiptBuilder(383);
        receipt.setTextSize(34).
                setAlign(Paint.Align.CENTER).
                setColor(Color.BLACK);
        if (priceTag.getProductPositioning().getId() != 0) {
            receipt.setTextSize(28).
                    addText("Kệ " + priceTag.getProductPositioning().getDisplayShelves().getDisSheID() + " Mâm " + priceTag.getProductPositioning().getDisplayPlatter().getDisPlaID() + " Vị trí " + priceTag.getProductPositioning().getId() + " Trưng " + priceTag.getProductPositioning().getForm())
                    .addLine();
        }
        receipt.addText(priceTag.getProductPositioning().getProduct().getProductName())
                .addImage(generateBarcode(priceTag.getProductPositioning().getProduct().getProductID(), 350, 70)).addParagraph()
                .setTextSize(50)
                .addText(decimalFormat.format(priceTag.getProductPositioning().getProduct().getPrice()) + "VND").addParagraph();
        return receipt.build();
    }

    /**
     * Tạo ảnh 1 tem giá có giảm giá
     *
     * @param priceTag Object Vị trí sản phẩm
     */
    public Bitmap generateOnePriceTagPurcent(PriceTag priceTag) {
        ReceiptBuilder receipt = new ReceiptBuilder(600);
        receipt.setMarginTop(0).
                setAlign(Paint.Align.CENTER).
                setColor(Color.BLACK);
        if (priceTag.getProductPositioning().getId() != 0) {
            receipt.setTextSize(28).
                    addText("Kệ " + priceTag.getProductPositioning().getDisplayShelves().getDisSheID() + " Mâm " + priceTag.getProductPositioning().getDisplayPlatter().getDisPlaID() + " Vị trí " + priceTag.getProductPositioning().getId() + " Trưng " + priceTag.getProductPositioning().getForm())
                    .addLine();
        }

        receipt.setTextSize(42)
                .addText(priceTag.getDiscountDetails().getDiscount().getDiscountType())
                .setTextSize(34)
                .addText(priceTag.getProductPositioning().getProduct().getProductName())
                .addImage(generateQr(priceTag.getProductPositioning().getProduct().getProductID(), 140, 140))
                .setTextSize(36)
                .addText(decimalFormat.format(priceTag.getProductPositioning().getProduct().getPrice()) + " VND giảm còn")
                .setTextSize(70)
                .addText(decimalFormat.format(priceTag.getProductPositioning().getProduct().getPrice() * ((100 - Double.parseDouble(priceTag.getDiscountDetails().getDisID())) / 100)) + " VND")
                .setTextSize(28)
                .addText("Từ ngày " + priceTag.getDiscountDetails().getStartTime() + " đến " + priceTag.getDiscountDetails().getEndTime());
        Bitmap invoice = receipt.build();
        Matrix matrix = new Matrix();

        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(invoice, 0, 0, invoice.getWidth(), invoice.getHeight(), matrix, true);
        return rotatedBitmap;
    }

    public Bitmap generateOnePriceTagGitfOne(PriceTag priceTag) {
        ReceiptBuilder receipt = new ReceiptBuilder(600);
        receipt.setMarginTop(0).
                setAlign(Paint.Align.CENTER).
                setColor(Color.BLACK);
        if (priceTag.getProductPositioning().getId() != 0) {
            receipt.setTextSize(28).
                    addText("Kệ " + priceTag.getProductPositioning().getDisplayShelves().getDisSheID() + " Mâm " + priceTag.getProductPositioning().getDisplayPlatter().getDisPlaID() + " Vị trí " + priceTag.getProductPositioning().getId() + " Trưng " + priceTag.getProductPositioning().getForm())
                    .addLine();
        }
        receipt.setTextSize(54)
                .addText(priceTag.getDiscountDetails().getDiscount().getDiscountType())
                .setTextSize(34)
                .addText(priceTag.getProductPositioning().getProduct().getProductName())
                .addImage(generateQr(priceTag.getProductPositioning().getProduct().getProductID(), 140, 140))
                .setTextSize(70)
                .addText(decimalFormat.format(priceTag.getProductPositioning().getProduct().getPrice() )+ " VND")
                .setTextSize(28)
                .addText("Từ ngày " + priceTag.getDiscountDetails().getStartTime() + " đến " + priceTag.getDiscountDetails().getEndTime());
        Bitmap invoice = receipt.build();
        Matrix matrix = new Matrix();

        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(invoice, 0, 0, invoice.getWidth(), invoice.getHeight(), matrix, true);
        return rotatedBitmap;
    }

    /**
     * Tạo ảnh 1 tem giá
     *
     * @param list Danh sách Vị trí sản phẩm
     */
    public ArrayList<Bitmap> generatePriceTags(List<ProductPositioning> list) {
        ArrayList<Bitmap> arrayList = new ArrayList<>();
        for (ProductPositioning productPositioning :
                list) {
            ReceiptBuilder receipt = new ReceiptBuilder(383);
            receipt.
                    setAlign(Paint.Align.CENTER).
                    setColor(Color.BLACK).
                    setTextSize(28).
                    addText("Kệ " + productPositioning.getDisplayShelves().getDisSheID() + "Mâm " + productPositioning.getDisplayPlatter().getDisPlaID() + "Vị trí " + productPositioning.getId() + "Trưng " + productPositioning.getForm())
                    .addLine()
                    .addText(productPositioning.getProduct().getProductName())
                    .addImage(generateBarcode(productPositioning.getProduct().getProductID(), 350, 70)).addParagraph()
                    .setTextSize(40)
                    .addText(productPositioning.getProduct().getPrice() + "").addParagraph();
            arrayList.add(receipt.build());
        }
        return arrayList;
    }

    public ArrayList<Bitmap> generatePriceTags2(List<PriceTag> list) {
        ArrayList<Bitmap> arrayList = new ArrayList<>();
        for (PriceTag priceTag :
                list) {
            if (priceTag.getDiscountDetails() != null) {
                if (priceTag.getDiscountDetails().getDisID().equals("2t1")) {
                    arrayList.add(generateOnePriceTagGitfOne(priceTag));
                } else {
                    arrayList.add(generateOnePriceTagPurcent(priceTag));
                }
            } else {
                arrayList.add(generateOnePriceTag(priceTag));
            }

        }
        return arrayList;
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
                addText("Nhân viên: Nguyễn Minh Thư").addParagraph().addLine().addText("Số lượng", false).setAlign(Paint.Align.CENTER)
                .addText("Giá bán", false).setAlign(Paint.Align.RIGHT).addText("Thành tiền").addText(" ")
                .setAlign(Paint.Align.LEFT).addText("Doanh nghiệp của thế kí 21").addText(" ").setAlign(Paint.Align.LEFT)
                .addText("1", false)
                .setAlign(Paint.Align.CENTER).addText("145.000", false)
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
                .addImage(generateBarcode("1234567890123", 300, 70))
                .addParagraph()
                .addLine().addText(" ")
                .addText(
                        "Cảm ơn quý khách!");
        return receipt.build();

    }

    /**
     * Tạo ảnh hóa đơn để chia sẻ
     *
     * @param bill       Object hóa đơn
     * @param detailList Danh sách sản phẩm trong hóa đơn
     */
    public Bitmap SharedBillPNG(Bill bill, List<BillDetail> detailList) {
        ReceiptBuilder receipt = new ReceiptBuilder(1200);
        receipt.setMarginTop(40).setMarginLeft(15).setMarginRight(15).setMarginBottom(30).
                setAlign(Paint.Align.CENTER).
                setColor(Color.BLACK).
                setTextSize(60).
                addText(bill.getStore().getAddress()).
                setTextSize(50).
                addLine().setTextSize(60).addText("Phiếu Thanh Toán").addParagraph().
                setAlign(Paint.Align.LEFT).
                addText("Số CT: " + bill.getBillID()).
                addText("Ngày CT: " + bill.getTimeCreate()).
                addText("Nhân viên: " + bill.getEmployee().getEmployeeName())
                .addParagraph().addLine()
                .addText("Số lượng", false)
                .setAlign(Paint.Align.CENTER)
                .addText("Giá bán", false).setAlign(Paint.Align.RIGHT).addText("Thành tiền").addText(" ")

        ;
        for (BillDetail billDetail : detailList
        ) {
            receipt
                    .setAlign(Paint.Align.LEFT).addText(billDetail.getProduct().getProductName())
                    .addText(" ")
                    .setAlign(Paint.Align.LEFT)
                    .addText(billDetail.getQuantity() + "", false)
                    .setAlign(Paint.Align.CENTER)
                    .addText(billDetail.getProduct().getPrice() + "", false)
                    .setAlign(Paint.Align.RIGHT)
                    .addText(billDetail.getTotalAmount() + "").addText(" ");
        }

        receipt
                .addParagraph()
                .addLine()
                .setAlign(Paint.Align.LEFT)
                .addText("Ví: 0")
                .addText("Tổng tiền: " + bill.getTotalAmount())
                .addText("Tiền mặt: " + bill.getCash())
                .addText("Tiền thối: " + bill.getReduced())
                .addText(" ")
                .addLine()
                .addText(" ")
                .setAlign(Paint.Align.CENTER)
                .addText("Mã hóa đơn/ Hóa đơn điện tử")
                .addImage(generateBarcode(bill.getBillID(), 600, 140))
                .addParagraph()
                .addLine()
                .addText(" ")
                .addText(
                        "Cảm ơn quý khách!");
        return receipt.build();

    }

    /**
     * Tạo mã barcode
     *
     * @param barcode Mã sản phẩm
     * @param width   Chiều rộng
     * @param height  Chiều cao
     */
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

    private Bitmap generateQr(String barcode, int width, int height) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(barcode, BarcodeFormat.QR_CODE, width, height);
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
