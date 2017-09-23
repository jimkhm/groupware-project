package spring.mybatis.gw.security.util;

public class SecurityEx {

	public static void main(String[] args) throws Exception {

		SecurityUtil securityUtil = new SecurityUtil();
			
		String rtn1 = securityUtil.encryptSHA256("hg741111#!");
		/*test*/System.out.println(">" + rtn1);
		
		String rtn2 = securityUtil.encryptSHA256("hg741111#!");
		/*test*/System.out.println(">" + rtn2);
		
		if( rtn1.equals(rtn2) ){
			/*test*/System.out.println(">>> Equals!");
		} else {
			/*test*/System.out.println(">>> Not Equals!");
		}
		
		String rtn3 = securityUtil.encryptSHA256("1111");
		/*test*/System.out.println(">" + rtn3);
		
		String rtn4 = securityUtil.encryptSHA256("1110");
		/*test*/System.out.println(">" + rtn4);
		
		if( rtn3.equals(rtn4) ){
			/*test*/System.out.println(">>> Equals!");
		} else {
			/*test*/System.out.println(">>> Not Equals!");
		}
		
	}
}
