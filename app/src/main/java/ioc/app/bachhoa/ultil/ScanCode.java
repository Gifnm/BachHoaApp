//package ioc.app.bachhoa.ultil;
//
//import android.content.Context;
//
//import androidx.activity.result.ActivityResultLauncher;
//
//import com.journeyapps.barcodescanner.ScanContract;
//import com.journeyapps.barcodescanner.ScanOptions;
//
//public class ScanCode {
//    private Context context;
//    public void ScanCode() {
//        ScanOptions scanOptions = new ScanOptions();
//        scanOptions.setPrompt("Quét mã Barcode và QR code");
//        scanOptions.setBeepEnabled(true);
//        scanOptions.setOrientationLocked(true);
//        scanOptions.setCaptureActivity(CaptureAct.class);
//        activityResultLauncher.launch(scanOptions);
//
//    }
//    ActivityResultLauncher<ScanOptions> activityResultLauncher = context.registerForActivityResult(new ScanContract(), result -> {
//        if (result.getContents() != null) {
//            //barcode.setText(result.getContents());
//        }
//
//    });
//}
