package com.example.apptourdulich;

import com.google.type.DateTime;

import java.util.Date;

public class KhachHang {

    private String HoTen;
    private String Gioitinh;
    private String NgaySinh;
    private String SDT;
    private String CMND;
    private String DiaChi;
    private String Imageid;
    public KhachHang(){
    }
public KhachHang(String hoTen,String gioitinh,String cMND,String sdt,String ngaySinh,String diaChi,String imageid)
{
    this.HoTen=hoTen;
    this.DiaChi=diaChi;
    this.CMND=cMND;
    this.Gioitinh=gioitinh;
    this.NgaySinh=ngaySinh;
    this.SDT=sdt;
    this.Imageid=imageid;
}
    public String getImageid() {
        return Imageid;
    }

    public void setImageid(String imageid) {
        Imageid = imageid;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getGioitinh() {
        return Gioitinh;
    }

    public void setGioitinh(String gioiTinh) {
        Gioitinh = gioiTinh;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }
}
