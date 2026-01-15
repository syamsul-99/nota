import java.util.ArrayList;
import java.util.Scanner;

class MenuItem {
    String nama;
    double harga;

    MenuItem(String nama, double harga) {
        this.nama = nama;
        this.harga = harga;
    }
}

class OrderItem {
    MenuItem menu;
    int jumlah;

    OrderItem(MenuItem menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }

    double hitungTotal() {
        return menu.harga * jumlah;
    }
}

class Nota {
    ArrayList<OrderItem> pesanan = new ArrayList<>();

    // --- MODIFIKASI: Tambahkan konstanta untuk pajak dan layanan ---
    // Ini praktik yang baik agar mudah diubah jika tarifnya ganti.
    final double RATE_PAJAK = 0.11; // Pajak PPN 11%
    final double RATE_LAYANAN = 0.05; // Biaya Layanan 5%
    // -------------------------------------------------------------

    void tambahPesanan(MenuItem menu, int jumlah) {
        pesanan.add(new OrderItem(menu, jumlah));
    }

    // --- MODIFIKASI: Ganti nama metode agar lebih akurat ---
    // Metode ini sekarang hanya menghitung subtotal (total harga barang)
    double hitungSubtotal() {
        double subtotal = 0;
        for (OrderItem o : pesanan) {
            subtotal += o.hitungTotal();
        }
        return subtotal;
    }
    // -------------------------------------------------------

    // --- MODIFIKASI: Metode baru untuk menghitung tambahan ---
    double hitungPajak(double subtotal) {
        return subtotal * RATE_PAJAK;
    }

    double hitungLayanan(double subtotal) {
        return subtotal * RATE_LAYANAN;
    }

    double hitungTotalBayar(double subtotal, double pajak, double layanan) {
        return subtotal + pajak + layanan;
    }
    // ---------------------------------------------------------

    void cetakNota() {
        System.out.println("\n=== NOTA PEMESANAN RESTORAN ===");
        for (OrderItem o : pesanan) {
            System.out.printf("%-20s x%d\tRp%.2f\n",
                    o.menu.nama, o.jumlah, o.hitungTotal());
        }
        System.out.println("-----------------------------------"); // Dipanjangkan sedikit

        // --- MODIFIKASI: Logika cetak nota diperbarui ---
        double subtotal = hitungSubtotal();
        double pajak = hitungPajak(subtotal);
        double layanan = hitungLayanan(subtotal);
        double totalAkhir = hitungTotalBayar(subtotal, pajak, layanan);

        System.out.printf("Subtotal:\t\t\tRp%.2f\n", subtotal);
        // Menampilkan persentase pajaknya juga
        System.out.printf("Pajak (%.0f%%):\t\t\tRp%.2f\n", RATE_PAJAK * 100, pajak);
        System.out.printf("Layanan (%.0f%%):\t\tRp%.2f\n", RATE_LAYANAN * 100, layanan);
        System.out.println("-----------------------------------");
        System.out.printf("TOTAL BAYAR:\t\t\tRp%.2f\n", totalAkhir);
        // --------------------------------------------------
    }
}

public class RestaurantOrder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Daftar menu
        MenuItem[] menuList = {
                new MenuItem("nasi", 8000),
                new MenuItem("ayam", 10000),
                new MenuItem("rendang", 15000),
                new MenuItem("Es Teh", 5000),
                new MenuItem("air mineral", 10000)
        };

        Nota nota = new Nota();


        System.out.println("=== MENU RESTORAN ===");
        for (int i = 0; i < menuList.length; i++) {
            System.out.printf("%d. %-15s Rp%.0f\n", i + 1, menuList[i].nama, menuList[i].harga);
        }

        while (true) {
            System.out.print("\nPilih nomor menu (0 untuk selesai): ");
            int pilih = sc.nextInt();
            if (pilih == 0) break;
            if (pilih < 1 || pilih > menuList.length) {
                System.out.println("Pilihan tidak valid!");
                continue;
            }
            System.out.print("Jumlah: ");
            int jml = sc.nextInt();
            nota.tambahPesanan(menuList[pilih - 1], jml);
        }
        nota.cetakNota();
        sc.close(); // MODIFIKASI: Sebaiknya Scanner ditutup di akhir
    }
}
//rumus total live tempelet
