package org.example.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Polaznik")
public class Polaznik {
    @Id
    @Column(name = "PolaznikID")
    private int polaznikID;

    @Column(name = "Ime", nullable = false, length = 100)
    private String ime;

    @Column(name = "Prezime", nullable = false, length = 100)
    private String prezime;

    @OneToMany(mappedBy = "polaznik", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Upis> upisi;

    public Polaznik() {}

    public Polaznik(int polaznikID, String ime, String prezime) {
        this.polaznikID = polaznikID;
        this.ime = ime;
        this.prezime = prezime;
    }

    public int getPolaznikID() { return polaznikID; }
    public void setPolaznikID(int polaznikID) { this.polaznikID = polaznikID; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public List<Upis> getUpisi() { return upisi; }
    public void setUpisi(List<Upis> upisi) { this.upisi = upisi; }

    @Override
    public String toString() {
        return "Polaznik{" +
                "polaznikID=" + polaznikID +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                '}';
    }
}