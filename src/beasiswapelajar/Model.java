/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beasiswapelajar;

/**
 *
 * @author Yepta
 */
public class Model {
    
    public int id;
    private String nama;
    private String deskripsi;
    private int nominal;

    public Model(int id, String nama, String deskripsi, int nominal) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.nominal = nominal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }
    
    
    
}
