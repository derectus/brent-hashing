/**
 * 
 */
package com.nuricilengir.brentsMethodimpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author derectus
 *
 */
public class Demo {

	private static Scanner scanner;

	/**
	 * @param Brent's Method Demo
	 */

	public static void main(String[] args) {
		try {
			brentHashDemo();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static <K, V> void brentHashDemo() throws FileNotFoundException {
		scanner = new Scanner(System.in);
		BrentsHashMap<K, V> brentHash = new BrentsHashMap<K, V>();
		brentHash.readFile();
		System.out.println();
		try {
			while (!false) {
				System.out.print("Please Enter Search Value : ");
				@SuppressWarnings("unchecked")
				V search = (V) scanner.next().toLowerCase();
				brentHash.bucketGet(brentHash.hashFuction(search), search);

			}
		} catch (Exception e) {
			System.out.println("Something went wrong !");
		}
	}
}
