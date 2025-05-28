package org.example.model;

import jakarta.persistence.*;
import org.example.model.*;

@Entity
@Table(name = "Upis")
public class Upis {
    @Id
    @Column(name = "UpisID")
    private int upisID;

    @ManyToOne
    @JoinColumn(name = "IDProgramObrazovanja", nullable = false)
    private ProgramObrazovanja programObrazovanja;

    @ManyToOne
    @JoinColumn(name = "IDPolaznik", nullable = false)
    private Polaznik polaznik;

    public Upis() {}

    public Upis(int upisID, ProgramObrazovanja programObrazovanja, Polaznik polaznik) {
        this.upisID = upisID;
        this.programObrazovanja = programObrazovanja;
        this.polaznik = polaznik;
    }

    public int getUpisID() { return upisID; }
    public void setUpisID(int upisID) { this.upisID = upisID; }

    public ProgramObrazovanja getProgramObrazovanja() { return programObrazovanja; }
    public void setProgramObrazovanja(ProgramObrazovanja programObrazovanja) { this.programObrazovanja = programObrazovanja; }

    public Polaznik getPolaznik() { return polaznik; }
    public void setPolaznik(Polaznik polaznik) { this.polaznik = polaznik; }

    @Override
    public String toString() {
        return "Upis{" +
                "upisID=" + upisID +
                ", programObrazovanja=" + programObrazovanja.getNaziv() +
                ", polaznik=" + polaznik.getIme() + " " + polaznik.getPrezime() +
                '}';
    }
}