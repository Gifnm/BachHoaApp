package ioc.app.bachhoa.DTOEntity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class PrintImageUsingSocket {
    public void print() {
        String printerHost = "192.168.1.7"; // Thay thế bằng địa chỉ IP hoặc tên máy chủ của máy in
        int printerPort = 9100; // Thay thế bằng cổng máy in thích hợp

        try {

            Socket socket = new Socket(printerHost, printerPort);
//            OutputStream outputStream = socket.getOutputStream();
//            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
//
//            // Chuyển đổi bitmap thành mảng byte
//            byte[] imageData = convertBitmapToByteArray(byteArrayToBitmap());
//
//            // Gửi dữ liệu hình ảnh đến máy in
//            dataOutputStream.write(imageData);
//
//            // Đóng outputStream, dataOutputStream và socket
//            dataOutputStream.close();
//            outputStream.close();
            OutputStream outputStream = socket.getOutputStream();

            // Chuỗi văn bản cần in
            String textToPrint = "Hello, Printer!Hello, Printer!\n";

            // Chuyển chuỗi văn bản thành mảng byte và ghi vào luồng đầu ra
            byte[] data = textToPrint.getBytes();
            outputStream.write(data);

            // Đóng luồng và kết nối
            outputStream.close();
            socket.close();

            System.out.println("In ảnh thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm chuyển đổi Bitmap thành mảng byte
    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
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
}
