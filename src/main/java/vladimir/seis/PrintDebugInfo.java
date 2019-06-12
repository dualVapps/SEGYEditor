package main.java.vladimir.seis;


// TODO Application of this class is question.....
// Класс для консольного отображения данных из начальной версии

import akka.Done;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.IOResult;
import akka.stream.Materializer;
import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;

import main.java.vladimir.seis.segystream.SegyConfig;
import main.java.vladimir.seis.segystream.SegyFlow;
import main.java.vladimir.seis.segystream.SegyHeaders;
import main.java.vladimir.seis.segystream.SegyPart;
import scala.concurrent.Future;

import javax.swing.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletionStage;





public class PrintDebugInfo {



    public static void main(String[] args) {

        JFrame mainJFrame = new JFrame("mainGui");
        mainJFrame.setContentPane(new mainGui().mainJPanel);
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainJFrame.pack();
        mainJFrame.setVisible(true);


        Path path;
        if (args != null && args.length >= 1) {
            path = Paths.get(args[0]);
        } else {
            throw new RuntimeException("SegY file path should be first param in args");
        }

        // Akka streams setup
        final ActorSystem system = ActorSystem.create("segystream-examples");
        final Materializer materializer = ActorMaterializer.create(system);

        // Construct stream source from file
        Source<ByteString, CompletionStage<IOResult>> fileSource = FileIO.fromPath(path);

        // Configure and declare the stream
        SegyConfig config = new SegyConfig(
                Charset.forName("CP037"), //textual data charset
                1024 //data chunk size, bytes
        );
        Source<SegyPart, Future<SegyHeaders>> segySource = fileSource.viaMat(new SegyFlow(config), Keep.right());

        // Run the stream
        CompletionStage<Done> done = segySource
                .map(segy -> {
                    System.out.println(segy.info()); // Print debug info
                    return segy;
                })
                .toMat(Sink.ignore(), Keep.right()) // wait for the Sink to complete
                .run(materializer);

        // Wait for stream termination and print the stats
        done.thenRun(system::terminate);
    }

}
