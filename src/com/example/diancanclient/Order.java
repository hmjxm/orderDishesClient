package com.example.diancanclient;

import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

public class Order {
	private Integer did;
	private String dname;  
	private String dtype; 
	private Integer dprice; 
    private String dpicture;
	private Integer quantity; 
	private Bitmap dbitmap;
	private String dintro;
	private String dflag;
    
	public Order(Integer did,String dname, String dtype, Integer dprice,String dpicture,Integer quantity, Bitmap dbitmap,String dintro,String dflag) {  
        super();  
        this.did=did;
        this.dname = dname;  
        this.dtype = dtype;  
        this.dprice = dprice; 
        this.dpicture = dpicture;
        this.quantity=quantity;
        this.dbitmap=dbitmap;
        this.dintro=dintro;
        this.dflag=dflag;
    }  
  
    public String getDname() {  
        return dname;  
    }  
  
    public void setDname(String dname) {  
        this.dname = dname;  
    }  
  
    public String getDtype() {  
        return dtype;  
    }  
  
    public void setDtype(String dtype) {  
        this.dtype = dtype;  
    }  
  
    public Integer getDprice() {  
        return dprice;  
    }  
  
    public void setDprice(Integer dprice) {  
        this.dprice = dprice;  
    }  
   
    public String getDpicture() {  
        return dpicture;  
    }  
  
    public void setDpicture(String Dpicture) {  
        this.dpicture = dpicture;  
    }
    
    public Integer getQuantity() {  
        return quantity;  
    }  
  
    public void setQuantity(Integer quantity) {  
        this.quantity = quantity;  
    }  
    
    
    public Integer getDid() {  
        return did;  
    }  
  
    public void setDid(Integer did) {  
        this.did = did;  
    }  
    
    public Bitmap getDbitmap() {  
        return dbitmap;  
    }  
  
    public void setDbitmap(Bitmap dbitmap) {  
        this.dbitmap = dbitmap;  
    }  
    
    
    public String getDintro() {  
        return dintro;  
    }  
  
    public void setDintro(String Dintro) {  
        this.dintro = dintro;  
    }
    
    public String getDflag() {  
        return dflag;  
    }  
  
    public void setDflag(String Dflag) {  
        this.dflag = dflag;  
    }
}
