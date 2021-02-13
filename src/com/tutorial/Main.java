package com.tutorial;

// import library java
import java.io.IOException;
import java.util.Scanner;

// import library CRUD
import CRUD.Operasi;
import CRUD.Utility;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner terminalInput = new Scanner(System.in);
        String pilihanUser;
        boolean isLanjutkan = true;

        while(isLanjutkan) {
            Utility.clearScreen();
            System.out.println("Database Perpustakaan\n");
            System.out.println("1.\tLihat seluruh buku");
            System.out.println("2.\tCari data buku");
            System.out.println("3.\tTambah data buku");
            System.out.println("4.\tUbah data buku");
            System.out.println("5.\tHapus data buku");

            System.out.print("\n\nPilihan anda: ");
            pilihanUser = terminalInput.next();

            switch (pilihanUser) {
                case "1":
                    System.out.println("\n==================");
                    System.out.println("LIST SELURUH BUKU");
                    System.out.println("==================");
                    //tampil data
                    Operasi.tampilkanData();
                    break;
                case "2":
                    System.out.println("\n==================");
                    System.out.println("CARI BUKU");
                    System.out.println("==================");
                    //cari data
                    Operasi.cariData();
                    break;
                case "3":
                    System.out.println("\n==================");
                    System.out.println("TAMBAH BUKU");
                    System.out.println("==================");
                    //tambah data
                    Operasi.tambahData();
                    Operasi.tampilkanData();
                    break;
                case "4":
                    System.out.println("\n==================");
                    System.out.println("UBAH BUKU");
                    System.out.println("==================");
                    //ubah data
                    Operasi.updateData();
                    break;
                case "5":
                    System.out.println("\n==================");
                    System.out.println("HAPUS BUKU");
                    System.out.println("==================");
                    //hapus data
                    Operasi.deleteData();
                    break;
                default:
                    System.err.println("Input tidak ditemukan!\nSilahkan pilih [1-5]");
                    break;
            }

            isLanjutkan = Utility.getYerOrNo("\nApakah anda ingin melanjutkan");
        }

    }

}
