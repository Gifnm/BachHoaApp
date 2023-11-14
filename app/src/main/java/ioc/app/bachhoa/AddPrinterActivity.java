package ioc.app.bachhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;

import ioc.app.bachhoa.api.PrinterAPI;
import ioc.app.bachhoa.model.Printers;
import ioc.app.bachhoa.model.Store;
import ioc.app.bachhoa.ultil.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPrinterActivity extends AppCompatActivity {
    private RadioButton page_medium;
    private EditText ipAddress, nameOfPrinter;
    private Button connect;
    private ImageView backActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_printer);
        uiInit();
        addEvent();

    }

    private void addEvent() {
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    Message message = new Message(AddPrinterActivity.this);
                    TcpConnection tcpConnection = new TcpConnection(ipAddress.getText().toString().trim(), 9100, 1000);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tcpConnection.connect();
                                Printers printers = new Printers();
                                printers.setStore(new Store(1));
                                printers.setNameOfPrinter(nameOfPrinter.getText().toString());
                                printers.setIpAdress(ipAddress.getText().toString());
                                printers.setPageSize(page_medium.isChecked() ? 57 : 80);
                                PrinterAPI.apiService.insert(printers).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            message.messageSucceed(connect, "Kết nối thành công!");
                                            finish();
                                        } else if (response.code() == 409) {
                                            message.messageFailed(connect, "Máy in đã tồn tại!");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            } catch (EscPosConnectionException e) {
                                message.messageFailed(connect, "Kết nối thất bại");

                                Log.e("printe2", e + "");
                            }

                        }
                    }).start();
                }
            }
        });
        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void uiInit() {
        page_medium = findViewById(R.id.aap_page_medium);
        ipAddress = findViewById(R.id.aap_ip_address);
        nameOfPrinter = findViewById(R.id.aap_name_of_printer);
        connect = findViewById(R.id.aap_connect);
        backActivity = findViewById(R.id.aap_back_button);
        page_medium.setChecked(true);


    }

    private boolean checkForm() {
        if (ipAddress.getText().toString().isEmpty()) {
            Message message = new Message(this);
            message.messageFailed(connect, "Hãy điền địa chỉ IP máy in!");
            return false;
        }
        if (nameOfPrinter.getText().toString().isEmpty()) {
            Message message = new Message(this);
            message.messageFailed(connect, "Hãy nhập tên máy in!");
            return false;
        }
        return true;
    }
}