package pcd.lab08.rx;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

/**
 * This examples shows why PublishSubject should be used carefully,
 * since they don't have a declarative semantics during assembly time,
 * but imperative.
 * 
 * That is: they are more like event bus.
 *
 * @author aricci
 *
 */
public class Test04b_pubsub_warning {

	public static void main(String[] args){
				
		Subject<String> subject = PublishSubject.create();
		
		subject.onNext("Alpha");
		subject.onNext("Beta");
		subject.onNext("Alpha");
		subject.onComplete();
		
		subject
		    .map(String::length)
			.subscribe(System.out::println);  // no output, too late 

	
		Subject<String> subject2 = PublishSubject.create();

		subject2.map(String::length)
			.subscribe(System.out::println);  // assembly must occur before producing
		
		subject2.onNext("One");
		subject2.onNext("Two");
		subject2.onNext("Three");
		subject2.onComplete();
		
		
;
	
	
	}
}
