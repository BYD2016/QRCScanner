package codepath.com.cn.qrcscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class MainActivity extends AppCompatActivity {

    public static final int REQ_CODE_SCANNER_QRC = 1;
    private TextView mTvScannerResult;

    private EditText mEtNeedContent;
    private ImageView mIvQRCPreview;
    CheckBox mCbRequireLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvScannerResult = (TextView) findViewById(R.id.tvScannerResult);
        mEtNeedContent = (EditText) findViewById(R.id.etNeedContent);
        mIvQRCPreview = (ImageView) findViewById(R.id.ivQRCPreview);
        mCbRequireLog = (CheckBox) findViewById(R.id.cbRequireLog);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String qrcResult = bundle.getString(CaptureActivity.EXTR_QRC_DECONE_RESULT);

            mTvScannerResult.setText(qrcResult);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 扫一扫
     * @param View
     */
    void onScannerClick(View View) {
        startActivityForResult(new Intent(this, CaptureActivity.class), REQ_CODE_SCANNER_QRC);
    }

    /**
     * 生成二维码
     * @param View
     */
    void onMakeQRCClick(View View) {

        String qrcSrc = mEtNeedContent.getText().toString();

        if (TextUtils.isEmpty(qrcSrc)) {
            Toast.makeText(this, "请输入要生成二维码的内容。", Toast.LENGTH_SHORT).show();
            if (mEtNeedContent.isFocusable()) {
                mEtNeedContent.requestFocus();
            }

            return;
        }

        Bitmap result = EncodingUtils.createQRCode(qrcSrc,
                mIvQRCPreview.getWidth(), mIvQRCPreview.getHeight(),
                mCbRequireLog.isChecked() ?
                          BitmapFactory.decodeResource(getResources(), R.drawable.imooc_logo)
                        : null);
        mIvQRCPreview.setImageBitmap(result);
    }
}
