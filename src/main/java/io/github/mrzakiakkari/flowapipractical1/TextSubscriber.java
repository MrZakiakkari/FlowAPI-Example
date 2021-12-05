package io.github.mrzakiakkari.flowapipractical1;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 *
 * @author M.Zaki Al Akkari <https://github.com/MrZakiakkari>
 */
public class TextSubscriber implements Subscriber<String>
{

	Subscription subscription;

	@Override
	public void onSubscribe(Flow.Subscription subscription)
	{
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(String word)
	{
		System.out.println(word);
		subscription.request(1);

	}

	@Override
	public void onError(Throwable throwable)
	{
		System.out.println(throwable.getMessage());
	}

	@Override
	public void onComplete()
	{
		System.out.println("Processing has completed");
	}

}
