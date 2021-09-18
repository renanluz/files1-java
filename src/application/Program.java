package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter file path: ");
		String sourceFileString = sc.nextLine();

		List<Product> list = new ArrayList<>();

		File sourceFile = new File(sourceFileString);
		String sourceFolderString = sourceFile.getParent();

		boolean sucess = new File(sourceFolderString + "\\out").mkdir();

		String targetFileString = sourceFolderString + "\\out\\summary.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileString))) {
			String itemCsv = br.readLine();

			while (itemCsv != null) {

				String infos[] = itemCsv.split(",");
				String name = infos[0];
				double price = Double.parseDouble(infos[1]);
				int quantity = Integer.parseInt(infos[2]);

				list.add(new Product(name, price, quantity));
				itemCsv = br.readLine();

			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileString))) {

				for (Product item : list) {
					bw.write(item.getName() + "," + String.format("%.2f", item.totalPrice()));
					bw.newLine();
				}

				System.out.println("Created!");

			} catch (IOException e) {
				System.out.println("Error wrinting file: " + e.getMessage());
			}

		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		sc.close();
	}
}
