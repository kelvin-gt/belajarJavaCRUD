package CRUD;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Operasi {

    public static void updateData() throws IOException {
        // kita ambil database original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        // kita buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        // tampilkan data
        System.out.println("List Buku");
        tampilkanData();

        // ambil user input / pilihan data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukkan nomor buku yang akan diupdate: ");
        int updateNum = terminalInput.nextInt();

        // tampilkan data yang ingin diupdate
        String data = bufferInput.readLine();
        int entryCount = 0;

        while (data != null) {
            entryCount++;

            StringTokenizer st = new StringTokenizer(data, ",");

            // tampilkan entryCount == updateNum
            if (updateNum == entryCount) {
                System.out.println("\nData yang ingin diupdate adalah:");
                System.out.println("---------------------------------------");
                System.out.println("Referensi          : " + st.nextToken());
                System.out.println("Tahun              : " + st.nextToken());
                System.out.println("Penulis            : " + st.nextToken());
                System.out.println("Penerbit           : " + st.nextToken());
                System.out.println("Judul              : " + st.nextToken());

                // update data

                // mengambil input user
                String[] fieldData = {"tahun","penulis","penerbit","judul"};
                String[] tempData = new String[4];

                // refresh token
                st = new StringTokenizer(data,",");
                String originalData = st.nextToken();

                for (int i = 0; i < fieldData.length; i++) {
                    boolean isUpdate = Utility.getYerOrNo("Apakah anda ingin mengubah " + fieldData[i]);

                    originalData = st.nextToken();
                    if (isUpdate) {

                        if (fieldData[i].equalsIgnoreCase("tahun")) {
                            System.out.print("Masukkan tahun terbit (yyyy) : ");
                            tempData[i] = Utility.ambilTahun();
                        }
                        else {
                            terminalInput = new Scanner(System.in);
                            System.out.print("\nMasukkan " + fieldData[i] + " baru: ");
                            tempData[i] = terminalInput.nextLine();
                        }

                    }
                    else {
                        tempData[i] = originalData;
                    }
                }

                // tampilkan data baru ke layar
                st = new StringTokenizer(data,",");
                st.nextToken();

                System.out.println("\nData baru anda adalah:");
                System.out.println("---------------------------------------");
                System.out.println("Tahun              : " + st.nextToken() + " --> " + tempData[0]);
                System.out.println("Penulis            : " + st.nextToken() + " --> " + tempData[1]);
                System.out.println("Penerbit           : " + st.nextToken() + " --> " + tempData[2]);
                System.out.println("Judul              : " + st.nextToken() + " --> " + tempData[3]);

                boolean isUpdate = Utility.getYerOrNo("Apa anda yakin mengupdate data tersebut? ");

                if (isUpdate) {
                    // cek data baru didatabase
                    boolean isExist = Utility.cekBukuDiDatabase(tempData,false);

                    if (isExist) {
                        System.err.println("Data buku sudah ada didatabase, proses update dibatalkan, \nSilahkan delete data yang bersangkutan");
                    }
                    else {
                        // format data baru ke dalam database
                        String tahun = tempData[0];
                        String penulis = tempData[1];
                        String penerbit = tempData[2];
                        String judul = tempData[3];

                        // kita bikin primary key
                        long nomorEntry = Utility.ambilEntryPertahun(penulis, tahun) + 1;

                        String penulisanTanpaSpasi = penulis.replaceAll("\\s+","");  // reguler expression JAVA
                        String primaryKey = penulisanTanpaSpasi + "_" + tahun + "_" + nomorEntry;

                        // tulis data ke database
                        bufferOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);

                    }
                }
                else {
                    // copy data
                    bufferOutput.write(data);
                }

            }
            else {
                // copy data
                bufferOutput.write(data);
            }
            bufferOutput.newLine();
            data = bufferInput.readLine();
        }

        // menulis data ke file
        bufferOutput.flush();
        bufferInput.close();
        bufferOutput.close();
        fileInput.close();
        fileOutput.close();

        System.gc();

        // delete original file
        database.delete();

        // renama file tempDB yg sementara menjadi original database
        tempDB.renameTo(database);

    }

    public static void deleteData() throws IOException {
        // kita ambil database original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        // kita buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        // tampil data
        System.out.println("List Buku");
        tampilkanData();

        // kita ambil userinput utk delete data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukkan nomor buku yang akan dihapus: ");
        int deleteNum = terminalInput.nextInt();

        // looping tuk baca tiap data baris dan skip data yg akan di delete
        boolean isFund = false;
        int entryCounts = 0;

        String data = bufferInput.readLine();

        while (data != null) {
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data,",");

            // tampilkan data yg ingin dihapus
            if (deleteNum == entryCounts) {
                System.out.println("\nData yang ingin dihapus adalah:");
                System.out.println("-----------------------------------");
                System.out.println("Referensi      : " + st.nextToken());
                System.out.println("Tahun          : " + st.nextToken());
                System.out.println("Penulis        : " + st.nextToken());
                System.out.println("Penerbit       : " + st.nextToken());
                System.out.println("Judul          : " + st.nextToken());

                isDelete = Utility.getYerOrNo("Apakah anda yakin akan menghapus? ");
                isFund = true;
            }

            if (isDelete) {
                // skip pindahkan dari dari original ke sementara
                System.out.println("Data berhasil dihapus");
            }
            else {
                // kita pindahkan data original ke sementara
                bufferOutput.write(data);
                bufferOutput.newLine();
            }
            data = bufferInput.readLine();


        }

        if (!isFund) {
            System.err.println("Buku tidak ditemukan");
        }

        // menulis data ke file
        bufferOutput.flush();
        bufferInput.close();
        bufferOutput.close();
        fileInput.close();
        fileOutput.close();

        System.gc();

        // delete original file
        database.delete();

        // renama file tempDB yg sementara menjadi original database
        tempDB.renameTo(database);

    }

    public static void tampilkanData() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("\nDatabase tidak ditemukan!");
            System.err.println("Silahkan tambah data terlebih dahulu!");
            tambahData();
            return;
        }

        System.out.println("\n| No |\tTahun |\tPenulis                |\tPenerbit               |\tJudul Buku ");
        System.out.println("----------------------------------------------------------------------------------------");

        String data = bufferInput.readLine();
        int nomorData = 0;

        while (data != null) {
            nomorData++;

            StringTokenizer stringToken = new StringTokenizer(data, ",");

            stringToken.nextToken();
            System.out.printf("| %2d ", nomorData);
            System.out.printf("|\t%4s  ", stringToken.nextToken());
            System.out.printf("|\t%-20s   ", stringToken.nextToken());
            System.out.printf("|\t%-20s   ", stringToken.nextToken());
            System.out.printf("|\t%s   ", stringToken.nextToken());
            System.out.print("\n");

            data = bufferInput.readLine();
        }
        System.out.println("----------------------------------------------------------------------------------------");

        bufferInput.close();
        fileInput.close();
    }

    public static void cariData() throws IOException {

        //membaca database ada atau tidak
        try {
            File file = new File("database.txt");
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan!");
            System.err.println("Silahkan tambah data terlebih dahulu!");
            return;
        }

        // kita ambil keyword dari user
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukkan kata kunci untuk mencari buku: ");
        String cariString = terminalInput.nextLine();
        String[] keywords = cariString.split("\\s+");

        // cek keyword didatabase
        Utility.cekBukuDiDatabase(keywords,true);


    }

    public static void tambahData() throws IOException {
        FileWriter fileOutput = new FileWriter("database.txt",true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);


        // mengambil input dari user
        Scanner terminalInput = new Scanner(System.in);
        String penulis, judul, tahun, penerbit;

        System.out.print("Masukkan nama penulis        : ");
        penulis = terminalInput.nextLine();
        System.out.print("Masukkan judul buku          : ");
        judul = terminalInput.nextLine();
        System.out.print("Masukkan penerbit buku       : ");
        penerbit = terminalInput.nextLine();
        System.out.print("Masukkan tahun terbit (yyyy) : ");
        tahun = Utility.ambilTahun();

        // cek buku didatabase
        String[] keywords = {tahun + "," + penulis + "," + penerbit + "," + judul};
        System.out.println(Arrays.toString(keywords));

        boolean isExist = Utility.cekBukuDiDatabase(keywords,false);

        // menulis buku di database
        if (!isExist) {
            // fiersabesari_2012_1,2012,fiersa besari,media kita,jejak langkah
            System.out.println(Utility.ambilEntryPertahun(penulis, tahun));
            long nomorEntry = Utility.ambilEntryPertahun(penulis, tahun) + 1;

            String penulisanTanpaSpasi = penulis.replaceAll("\\s+","");  // reguler expression JAVA
            String primaryKey = penulisanTanpaSpasi + "_" + tahun + "_" + nomorEntry;
            System.out.println("\nData yang akan anda masukan adalah");
            System.out.println("--------------------------------------------");
            System.out.println("primary key  : " + primaryKey);
            System.out.println("tahun terbit : " + tahun);
            System.out.println("penulis      : " + penulis);
            System.out.println("judul        : " + judul);
            System.out.println("penerbit     : " + penerbit);

            boolean isTambah = Utility.getYerOrNo("Apakah ingin menambah data tersebut? ");

            if(isTambah) {
                bufferOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                bufferOutput.newLine();
                bufferOutput.flush();
            }
        }
        else {
            System.out.println("buku yang anda masukan sudah tersedia di database dgn data berikut:");
            Utility.cekBukuDiDatabase(keywords,true);
        }

        bufferOutput.close();
        fileOutput.close();
    }
}
