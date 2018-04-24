package com.hp.augmentedprint.schema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QrDetails {

@SerializedName("result")
@Expose
private String result;

@SerializedName("data")
@Expose
private QrData data;

@SerializedName("message")
@Expose
private String message;

public String getResult() {
return result;
}

public void setResult(String result) {
this.result = result;
}

public QrData getData() {
return data;
}

public void setData(QrData data) {
this.data = data;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}