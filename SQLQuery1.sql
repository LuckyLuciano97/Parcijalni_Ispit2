CREATE DATABASE JavaAdv;

USE JavaAdv;

CREATE TABLE Polaznik (
    PolaznikID INT PRIMARY KEY,
    Ime NVARCHAR(100) NOT NULL,
    Prezime NVARCHAR(100) NOT NULL
);

CREATE TABLE ProgramObrazovanja (
    ProgramObrazovanjaID INT PRIMARY KEY,
    Naziv NVARCHAR(100) NOT NULL,
    CSVET INT NOT NULL
);

CREATE TABLE Upis (
    UpisID INT PRIMARY KEY,
    IDProgramObrazovanja INT NOT NULL,
    IDPolaznik INT NOT NULL,
    FOREIGN KEY (IDProgramObrazovanja) REFERENCES ProgramObrazovanja(ProgramObrazovanjaID),
    FOREIGN KEY (IDPolaznik) REFERENCES Polaznik(PolaznikID)
);

SELECT * FROM Polaznik;