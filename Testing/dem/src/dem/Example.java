package dem;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Example {
public static void main(String[] args) {
	String str = "statica";
	List<Integer>list=Arrays.asList(2,6,4,5,9,8);
	Map<Boolean,List<Integer>>ans=list.stream().collect(Collectors.groupingBy(e->e%2==0));
	System.out.println(ans);
}
}
