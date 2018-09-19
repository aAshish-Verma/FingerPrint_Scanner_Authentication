package Biometrics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    /**
     * The Constant logger.
     */
    private static volatile boolean isLinuxOrMac = false;
    private static volatile boolean isWindows = false;

    /**
     * Fitler null string.
     *
     * @param array the array
     * @return the string[]
     */
    public static String[] fitlerNullString(String[] array) {
        List<String> list = new ArrayList<String>();
        for (String c : array) {
            if (c != null && c.length() > 0) {
                list.add(c);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Gain samle.
     *
     * @param bytes the bytes
     * @return the byte[]
     */
    public static byte[] gainSample(byte[] bytes, double factor) {
        short[] out = new short[bytes.length / 2];
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < out.length; i++) {
            out[i] = (short) (bb.getShort() * factor);
        }
        ByteBuffer a = ByteBuffer.allocate(bytes.length);
        a.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < out.length; i++) {
            a.putShort(out[i]);
        }
        return a.array();
    }

    /**
     * Generate random array.
     *
     * @return the byte[]
     */
    public static byte[] generateRandomArray() {
        int size = 20000;
        byte[] byteArray = new byte[size];
        for (int i = 0; i < size; i++) {
            byteArray[i] = (byte) (Math.random() * 127f);
        }

        return byteArray;
    }

    /**
     * Gets the all threads.
     *
     * @return the all threads
     */
    private static Thread[] getAllThreads() {
        final ThreadGroup root = getRootThreadGroup();
        final ThreadMXBean thbean = ManagementFactory.getThreadMXBean();
        int nAlloc = thbean.getThreadCount();
        int n = 0;
        Thread[] threads;
        do {
            nAlloc *= 2;
            threads = new Thread[nAlloc];
            n = root.enumerate(threads, true);
        } while (n == nAlloc);
        return java.util.Arrays.copyOf(threads, n);
    }

    /**
     * Gets the root thread group.
     *
     * @return the root thread group
     */
    private static ThreadGroup getRootThreadGroup() {
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        ThreadGroup ptg;
        while ((ptg = tg.getParent()) != null) {
            tg = ptg;
        }
        return tg;
    }

    /**
     * Gets the thread.
     *
     * @return the thread
     */
    public static Thread getThread(final String name) {
        if (name == null) {
            throw new NullPointerException("Null name");
        }
        final Thread[] threads = getAllThreads();
        for (Thread thread : threads) {
            if (thread.getName().equals(name)) {
                return thread;
            }
        }
        return null;
    }

    /**
     * reads the number from file
     *
     * @return the String
     */
    public String gettingToCallNumber(String path) {
        String st = "";
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((st = br.readLine()) != null) {
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }

    public String gettingFlag(String path) {
        String st = "";
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i = 0;
            while ((st = br.readLine()) != null) {
                if (st.length() == 1) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }

    public static String gettingPath() {
        String tempFileDirectory = "";
        try {
            tempFileDirectory = System.getProperty("java.io.tmpdir");
            if ((tempFileDirectory.charAt(tempFileDirectory.length() - 1) != '\\')
                    && (tempFileDirectory.charAt(tempFileDirectory.length() - 1) != '/')) {
                tempFileDirectory += "/";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempFileDirectory;
    }

    public static void loadingRxtxToTempDir() {
        // Determine the temporary file directory for Java
        String OS = System.getProperty("os.name").toLowerCase();
        String libraryPath = "", fileName = "";
        String tempFileDirectory = System.getProperty("java.io.tmpdir");
        if ((tempFileDirectory.charAt(tempFileDirectory.length() - 1) != '\\')
                && (tempFileDirectory.charAt(tempFileDirectory.length() - 1) != '/')) {
            tempFileDirectory += "/";
        }
        System.out.println("temp : " + tempFileDirectory);
        // Force remove any previous versions of this library
        File directory = new File(tempFileDirectory);
        if (directory.exists()) {
            File directoryListing[] = directory.listFiles();
            for (File listing : directoryListing) {
                if (listing.toString().contains(".wav")) {
                    System.out.println("util wala WAV delete !");
                    listing.delete();
                }
            }
        }

        // Determine Operating System and architecture
        if (OS.indexOf("win") >= 0) {
            if (System.getProperty("os.arch").indexOf("64") >= 0) {
                libraryPath = "Windows/x86_64";
            } else {
                libraryPath = "Windows/x86";
            }
            isWindows = true;
            fileName = "rxtxSerial.dll";
        } else if ((OS.indexOf("nix") >= 0) || (OS.indexOf("nux") >= 0)) {
            if (System.getProperty("os.arch").indexOf("arm") >= 0) {
                // Determine the specific ARM architecture of this device
                try {
                    BufferedReader cpuPropertiesFile = new BufferedReader(new FileReader("/proc/cpuinfo"));
                    String line;
                    while ((line = cpuPropertiesFile.readLine()) != null) {
                        if (line.contains("ARMv")) {
                            libraryPath = "Linux/armv" + line.substring(line.indexOf("ARMv") + 4, line.indexOf("ARMv") + 5);
                            break;
                        } else if (line.contains("aarch")) {
                            libraryPath = "Linux/armv8";
                            break;
                        }
                    }
                    cpuPropertiesFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
     // Ensure that there was no error, and see if we need to use the hard-float dynamic linker
                if (libraryPath.isEmpty()) {
                    libraryPath = "Linux/armv6";
                } else if (libraryPath.contains("Linux/armv8")) {
                    libraryPath += (System.getProperty("os.arch").indexOf("64") >= 0) ? "_64" : "_32";
                } else {
                    try {
                        ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "ldd /usr/bin/ld | grep ld-");
                        Process p = pb.start();
                        p.waitFor();
                        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String linkLoader = br.readLine();
                        if (linkLoader.contains("armhf")) {
                            libraryPath += "-hf";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (System.getProperty("os.arch").indexOf("aarch32") >= 0) {
                libraryPath = "Linux/armv8_32";
            } else if (System.getProperty("os.arch").indexOf("aarch64") >= 0) {
                libraryPath = "Linux/armv8_64";
            } else if (System.getProperty("os.arch").indexOf("64") >= 0) {
                libraryPath = "Linux/x86_64";
            } else if (System.getProperty("os.arch").indexOf("86") >= 0) {
                libraryPath = "Linux/x86";
            } else {
                libraryPath = "Linux/x86";
            }
            isLinuxOrMac = true;
            fileName = "librxtxSerial.so";
        } else {
            System.err.println("This operating system is not supported by the jSerialComm library.");
            System.exit(-1);
        }
        // Get path of native library and copy file to working directory
        String tempFileName = tempFileDirectory + fileName;
        File tempNativeLibrary = new File(tempFileName);
        tempNativeLibrary.deleteOnExit();
        try {

            //copy ring wav to temp directory
            {
                InputStream fileContents = Utils.class.getResourceAsStream("ring.wav");
                if (fileContents == null) {
                    System.err.println("Could not locate or access the native jSerialComm shared library.");
                    System.err.println("If you are using multiple projects with interdependencies, you may need to fix your build settings to ensure that library resources are copied properly.");
                } else {
                    // Copy the native library to the system temp directory
                    try {
                        FileOutputStream destinationFileContents = new FileOutputStream(tempFileDirectory + "/ring.wav");
                        byte transferBuffer[] = new byte[4096];
                        int numBytesRead;

                        while ((numBytesRead = fileContents.read(transferBuffer)) > 0) {
                            destinationFileContents.write(transferBuffer, 0, numBytesRead);
                        }
                        destinationFileContents.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    };
                }
            }

            // Copy the FTDI driver library to the system temp directory if on Windows
            if (isWindows) {
                // Load the native jSerialComm library
                //InputStream fileContents = SerialPort.class.getResourceAsStream("/" + libraryPath + "/" + fileName);
                InputStream fileContents = Utils.class.getResourceAsStream(libraryPath + "/" + fileName);
                System.out.println(Utils.class.getResource(libraryPath + "/" + fileName));
                if (fileContents == null) {
                    System.err.println("Could not locate or access the native jSerialComm shared library.");
                    System.err.println("If you are using multiple projects with interdependencies, you may need to fix your build settings to ensure that library resources are copied properly.");
                } else {
                    // Copy the native library to the system temp directory
                    try {
                        FileOutputStream destinationFileContents = new FileOutputStream(tempNativeLibrary);
                        byte transferBuffer[] = new byte[4096];
                        int numBytesRead;

                        while ((numBytesRead = fileContents.read(transferBuffer)) > 0) {
                            destinationFileContents.write(transferBuffer, 0, numBytesRead);
                        }
                        destinationFileContents.close();
                    } catch (FileNotFoundException e) {
                    };
                }
            } else if (isLinuxOrMac) {
                // Load the native jSerialComm library
                //InputStream fileContents = SerialPort.class.getResourceAsStream("/" + libraryPath + "/" + fileName);
                InputStream fileContents = Utils.class.getResourceAsStream(libraryPath + "/" + fileName);
                System.out.println(Utils.class.getResource(libraryPath + "/" + fileName));
                if (fileContents == null) {
                    System.err.println("Could not locate or access the native jSerialComm shared library.");
                    System.err.println("If you are using multiple projects with interdependencies, you may need to fix your build settings to ensure that library resources are copied properly.");
                } else {
                    // Copy the native library to the system temp directory
                    try {
                        FileOutputStream destinationFileContents = new FileOutputStream(tempNativeLibrary);
                        byte transferBuffer[] = new byte[4096];
                        int numBytesRead;

                        while ((numBytesRead = fileContents.read(transferBuffer)) > 0) {
                            destinationFileContents.write(transferBuffer, 0, numBytesRead);
                        }
                        destinationFileContents.close();
                    } catch (FileNotFoundException e) {
                    };
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Method to find current datetime
    
    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
}
    
    // Method for resizing image 
    
    public static BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

    int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    BufferedImage ret = img;
    BufferedImage scratchImage = null;
    Graphics2D g2 = null;

    int w = img.getWidth();
    int h = img.getHeight();

    int prevW = w;
    int prevH = h;

    do {
        if (w > targetWidth) {
            w /= 2;
            w = (w < targetWidth) ? targetWidth : w;
        }

        if (h > targetHeight) {
            h /= 2;
            h = (h < targetHeight) ? targetHeight : h;
        }

        if (scratchImage == null) {
            scratchImage = new BufferedImage(w, h, type);
            g2 = scratchImage.createGraphics();
        }

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

        prevW = w;
        prevH = h;
        ret = scratchImage;
    } while (w != targetWidth || h != targetHeight);

    if (g2 != null) {
        g2.dispose();
    }

    if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
        scratchImage = new BufferedImage(targetWidth, targetHeight, type);
        g2 = scratchImage.createGraphics();
        g2.drawImage(ret, 0, 0, null);
        g2.dispose();
        ret = scratchImage;
    }

    return ret;

}
}
