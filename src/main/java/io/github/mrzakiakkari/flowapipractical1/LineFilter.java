package io.github.mrzakiakkari.flowapipractical1;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Predicate;

/**
 *
 * @author M.Zaki Al Akkari <https://github.com/MrZakiakkari>
 */
public class LineFilter extends SubmissionPublisher<String> implements Flow.Processor<String, String>
{
	private final Predicate<String> predicate;

	private Flow.Subscription subscription;
	LineFilter(Predicate<String> predicate)
	{
		this.predicate = predicate;
	}

	@Override
	public void onSubscribe(Flow.Subscription subscription)
	{
		this.subscription = subscription;
		this.subscription.request(1);
	}
	@Override
	public void onNext(String item)
	{
		if (predicate.test(item))
		{
			submit(item);
		}

		this.subscription.request(1);
	}
	@Override
	public void onError(Throwable throwable)
	{
		System.out.println("Error : " + throwable.getMessage());
		closeExceptionally(throwable);
	}
	@Override
	public void onComplete()
	{
		System.out.println("Line Filter on complete message");
		close();
	}

}
