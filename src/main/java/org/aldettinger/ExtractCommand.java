package org.aldettinger;

import picocli.CommandLine;

@CommandLine.Command
public class ExtractCommand implements Runnable {

    public ExtractCommand() {
    }

    @Override
    public void run() {
        System.out.println("Hello extraction world !");
    }
}
