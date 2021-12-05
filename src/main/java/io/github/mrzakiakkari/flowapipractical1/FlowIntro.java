package io.github.mrzakiakkari.flowapipractical1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author M.Zaki Al Akkari <https://github.com/MrZakiakkari>
 */
public class FlowIntro
{
	public static void main(String[] args)
	{
		SubmissionPublisher publisher = new SubmissionPublisher<>();
		Predicate<String> startsWithA = line -> line.startsWith("A");
		Processor lineAFilter = new LineFilter(startsWithA);
		publisher.subscribe(lineAFilter);
		Subscriber subcriber = new TextSubscriber();
		publisher.subscribe(subcriber);

		FlowIntro main = new FlowIntro();
		String filePath = "shakespeare.txt";
		BufferedReader br = main.getBufferedReaderForFile(filePath);
		Stream<String> linesOfText = br.lines();
		linesOfText.forEach(publisher::submit);
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException ex)
		{
			Logger.getLogger(FlowIntro.class.getName()).log(Level.SEVERE, null, ex);
		}

		publisher.close();

	}
	private BufferedReader getBufferedReaderForFile(String filePath)
	{

		File inFile = new File("src/main/resources/" + filePath);
		FileReader fr = null;

		try
		{
			fr = new FileReader(inFile);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error:" + ex);
		}
		return new BufferedReader(fr);
	}

}
