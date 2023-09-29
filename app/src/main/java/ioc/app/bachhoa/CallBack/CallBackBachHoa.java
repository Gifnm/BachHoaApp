package ioc.app.bachhoa.CallBack;

public interface CallBackBachHoa<T> {
    void onResponseReceived(T response);
}