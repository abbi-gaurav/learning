package jmx; 
 
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
 
public class Main { 
 
   public static void main(String[] args) throws Exception { 
 
      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
 
      ObjectName name = new ObjectName("com.example.mbeans:type=Hello"); 
 
      Hello mbean = new Hello(); 
 
      mbs.registerMBean(mbean, name); 
 
      System.out.println("Waiting forever..."); 
      Thread.sleep(Long.MAX_VALUE); 
   } 
} 