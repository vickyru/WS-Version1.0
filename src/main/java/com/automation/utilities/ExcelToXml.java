package com.automation.utilities;

/**
 * @author pt46958
 *
 */
public class ExcelToXml {

  /**
   * This class is responsible to drive the life cycle to the code and
   * to gnerat the testNG file.
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    try {
      TestNGCreator.createXml(args[0]);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}