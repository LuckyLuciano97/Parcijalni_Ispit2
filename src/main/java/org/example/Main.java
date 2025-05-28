package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.example.util.HibernateUtil;
import org.example.model.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            while (true) {
                prikaziMeni();
                int izbor = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (izbor) {
                    case 1:
                        unesiNovogPolaznika();
                        break;
                    case 2:
                        unesiNoviProgramObrazovanja();
                        break;
                    case 3:
                        upisiPolaznikaNaProgramObrazovanja();
                        break;
                    case 4:
                        prebaciPolaznikaIzJednogUDrugiProgramObrazovanja();
                        break;
                    case 5:
                        zadaniProgramObrazovanja();
                        break;
                    case 0:
                        System.out.println("Izlaz iz programa...");
                        return;
                    default:
                        System.out.println("Nevaljan izbor!");
                }
            }
        } finally {
            HibernateUtil.shutdown();
            scanner.close();
        }
    }

    private static void prikaziMeni() {
        System.out.println("\n=== IZBORNIK ===");
        System.out.println("1. Unesi novog polaznika");
        System.out.println("2. Unesi novi program obrazovanja");
        System.out.println("3. Upiši polaznika na program obrazovanja");
        System.out.println("4. Prebaci polaznika iz jednog u drugi program obrazovanja");
        System.out.println("5. Za zadani program obrazovanja, ispišite ime i prezime polaznika, naziv programa obrazovanja i broj CSVET bodova koje će polaznik koji ga upisali");
        System.out.println("0. Izlaz");
        System.out.print("Vaš izbor: ");
    }

    private static void unesiNovogPolaznika() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            System.out.print("Unesite ID polaznika: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Unesite ime polaznika: ");
            String ime = scanner.nextLine();

            System.out.print("Unesite prezime polaznika: ");
            String prezime = scanner.nextLine();

            Polaznik polaznik = new Polaznik(id, ime, prezime);
            session.save(polaznik);

            transaction.commit();
            System.out.println("Polaznik je uspešno dodat!");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Greška pri dodavanju polaznika: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    private static void unesiNoviProgramObrazovanja() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            System.out.print("Unesite ID programa obrazovanja: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Unesite naziv programa obrazovanja: ");
            String naziv = scanner.nextLine();

            System.out.print("Unesite broj CSVET bodova: ");
            int csvet = scanner.nextInt();

            ProgramObrazovanja program = new ProgramObrazovanja(id, naziv, csvet);
            session.save(program);

            transaction.commit();
            System.out.println("Program obrazovanja je uspešno dodat!");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Greška pri dodavanju programa obrazovanja: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    private static void upisiPolaznikaNaProgramObrazovanja() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            System.out.print("Unesite ID upisa: ");
            int upisId = scanner.nextInt();

            System.out.print("Unesite ID polaznika: ");
            int polaznikId = scanner.nextInt();

            System.out.print("Unesite ID programa obrazovanja: ");
            int programId = scanner.nextInt();

            Polaznik polaznik = session.get(Polaznik.class, polaznikId);
            ProgramObrazovanja program = session.get(ProgramObrazovanja.class, programId);

            if (polaznik == null) {
                System.out.println("Polaznik nije pronađen");
                return;
            }

            if (program == null) {
                System.out.println("Program obrazovanja nije pronađen");
                return;
            }

            Upis upis = new Upis(upisId, program, polaznik);
            session.save(upis);

            transaction.commit();
            System.out.println("Polaznik je uspešno upisan na program obrazovanja!");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Greška pri upisu polaznika: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    private static void prebaciPolaznikaIzJednogUDrugiProgramObrazovanja() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            System.out.print("Unesite ID polaznika: ");
            int polaznikId = scanner.nextInt();

            System.out.print("Unesite ID starog programa obrazovanja: ");
            int stariProgramId = scanner.nextInt();

            System.out.print("Unesite ID novog programa obrazovanja: ");
            int noviProgramId = scanner.nextInt();

            // Pronađi postojeći upis
            Query<Upis> query = session.createQuery(
                    "FROM Upis u WHERE u.polaznik.polaznikID = :polaznikId AND u.programObrazovanja.programObrazovanjaID = :programId",
                    Upis.class);
            query.setParameter("polaznikId", polaznikId);
            query.setParameter("programId", stariProgramId);

            List<Upis> upisi = query.getResultList();

            if (upisi.isEmpty()) {
                System.out.println("Polaznik nije pronađen");
                return;
            }

            Upis upis = upisi.get(0);
            ProgramObrazovanja noviProgram = session.get(ProgramObrazovanja.class, noviProgramId);

            if (noviProgram == null) {
                System.out.println("Novi program obrazovanja nije pronađen");
                return;
            }

            upis.setProgramObrazovanja(noviProgram);
            session.update(upis);

            transaction.commit();
            System.out.println("Polaznik je uspešno prebačen na novi program obrazovanja");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Greška pri prebacivanju polaznika: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    private static void zadaniProgramObrazovanja() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            System.out.print("Unesite ID programa obrazovanja: ");
            int programId = scanner.nextInt();

            Query<Object[]> query = session.createQuery(
                    "SELECT p.ime, p.prezime, po.naziv, po.csvet " +
                            "FROM Upis u " +
                            "JOIN u.polaznik p " +
                            "JOIN u.programObrazovanja po " +
                            "WHERE po.programObrazovanjaID = :programId",
                    Object[].class);
            query.setParameter("programId", programId);

            List<Object[]> rezultati = query.getResultList();

            if (rezultati.isEmpty()) {
                System.out.println("Nema polaznika upisanih na zadati program obrazovanja!");
            } else {
                System.out.println("\nPolaznici upisani na program obrazovanja:");
                System.out.println("==========================================");
                for (Object[] red : rezultati) {
                    String ime = (String) red[0];
                    String prezime = (String) red[1];
                    String nazivPrograma = (String) red[2];
                    int csvet = (Integer) red[3];

                    System.out.printf("Ime: %s, Prezime: %s, Program: %s, CSVET bodovi: %d%n",
                            ime, prezime, nazivPrograma, csvet);
                }
            }

        } catch (Exception e) {
            System.err.println("Greška pri pretraživanju: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}
