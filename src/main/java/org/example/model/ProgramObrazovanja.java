package org.example.model;

import jakarta.persistence.*;
import org.example.model.*;
import java.util.List;



@Entity
@Table(name = "ProgramObrazovanja")
public class ProgramObrazovanja {
    @Id
    @Column(name = "ProgramObrazovanjaID")
    private int programObrazovanjaID;

    @Column(name = "Naziv", nullable = false, length = 100)
    private String naziv;

    @Column(name = "CSVET", nullable = false)
    private int csvet;

    @OneToMany(mappedBy = "programObrazovanja", cascade = CascadeType.ALL)
    private List<Upis> upisi;

    public ProgramObrazovanja() {}

    public ProgramObrazovanja(int programObrazovanjaID, String naziv, int csvet) {
        this.programObrazovanjaID = programObrazovanjaID;
        this.naziv = naziv;
        this.csvet = csvet;
    }

    public int getProgramObrazovanjaID() { return programObrazovanjaID; }
    public void setProgramObrazovanjaID(int programObrazovanjaID) { this.programObrazovanjaID = programObrazovanjaID; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public int getCsvet() { return csvet; }
    public void setCsvet(int csvet) { this.csvet = csvet; }

    public List<Upis> getUpisi() { return upisi; }
    public void setUpisi(List<Upis> upisi) { this.upisi = upisi; }

    @Override
    public String toString() {
        return "ProgramObrazovanja{" +
                "programObrazovanjaID=" + programObrazovanjaID +
                ", naziv='" + naziv + '\'' +
                ", csvet=" + csvet +
                '}';
    }
}