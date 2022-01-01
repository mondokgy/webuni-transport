package hu.webuni.transport.security;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;

import hu.webuni.transport.config.TransportConfigProperties;

@Service
public class JwtService {


	private static final String AUTH = "auth";

	private Algorithm alg;
	private String issuer;
	
	@Autowired
	private TransportConfigProperties config;
	
	@PostConstruct
	public void init() {
		this.issuer = config.getJwt().getIssuer();
		try {
			this.alg = (Algorithm) Algorithm.class.getMethod(config.getJwt().getAlg(), String.class)
					.invoke(Algorithm.class, config.getJwt().getSecret());
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			
			e.printStackTrace();
		}
	
	}
	
	public String creatJwtToken(UserDetails principal) {
		return JWT.create()
			.withSubject(principal.getUsername())
			.withArrayClaim(AUTH, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
			.withExpiresAt(new Date(System.currentTimeMillis() + config.getJwt().getDuration().toMillis()))
			.withIssuer(issuer)
			.sign(Algorithm.HMAC256(config.getJwt().getSecret()));
	}

//	public UserDetails parseJwt(String jwtToken) {
//		
//		DecodedJWT decodedJwt = JWT.require(alg)
//			.withIssuer(issuer)
//			.build()
//			.verify(jwtToken);
//		
//		Employee employee = new Employee();
//		employee.setEmployeeID(decodedJwt.getClaim(ID).asLong());
//		employee.setName(decodedJwt.getClaim(FULLNAME).asString());
//		employee.setUsername(decodedJwt.getSubject());
//		Claim managerClaim = decodedJwt.getClaim(MANAGER);
//		if(managerClaim != null) {
//			Map<String, Object> managerData = managerClaim.asMap();
//			if(managerData != null) {
//				Employee manager = new Employee();
//				employee.setManager(manager);
//				manager.setEmployeeID(((Integer)managerData.get(ID)).longValue());
//				manager.setUsername(managerData.get(MANAGER_USERNAME).toString());
//			}
//		}
//		
//		Claim managedEmployeeUsernamesClaim = decodedJwt.getClaim(MANAGED_EMPLOYEE_USERNAMES);
//		if(managedEmployeeUsernamesClaim != null) {
//			employee.setManagedEmployees(new ArrayList<>());
//			List<String> managedEmployeeUsernames = managedEmployeeUsernamesClaim.asList(String.class);
//			if(managedEmployeeUsernames != null && !managedEmployeeUsernames.isEmpty()) {
//				List<Long> managedEmployeeIds = decodedJwt.getClaim(MANAGED_EMPLOYEE_IDS).asList(Long.class);
//				for(int i=0; i< managedEmployeeIds.size(); i++) {
//					Employee managedEmployee = new Employee();
//					managedEmployee.setUsername(managedEmployeeUsernames.get(i));
//					managedEmployee.setEmployeeID(managedEmployeeIds.get(i));
//					employee.getManagedEmployees().add(managedEmployee);
//				}
//			}
//		}
//		
//		
//		return new HrUser(decodedJwt.getSubject(), "dummy", 
//			decodedJwt.getClaim(AUTH).asList(String.class)
//				.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
//			, employee);
//	}

}
