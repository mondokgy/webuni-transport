package hu.webuni.transport.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="transport")
@Component
public class TransportConfigProperties {

	private JwtData jwt = new JwtData();
	
	public static class JwtData {
		private String issuer;
		private String secret;
		private String alg;
		private Duration duration;
		public String getIssuer() {
			return issuer;
		}
		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}
		public String getSecret() {
			return secret;
		}
		public void setSecret(String secret) {
			this.secret = secret;
		}
		public String getAlg() {
			return alg;
		}
		public void setAlg(String alg) {
			this.alg = alg;
		}
		public Duration getDuration() {
			return duration;
		}
		public void setDuration(Duration duration) {
			this.duration = duration;
		}
	}

	public JwtData getJwt() {
		return jwt;
	}

	public void setJwt(JwtData jwt) {
		this.jwt = jwt;
	}
	
	private List<Delay> delay = new ArrayList<Delay>();
	
	public List<Delay> getDelay() {
		return delay;
	}

	public void setPayCategory(List<Delay> delay) {
		this.delay = delay;
	}

	public static class Delay{
		private int min;
		private int percent;
		
		public int getPercent() {
			return percent;
		}

		public void setPercent(int percent) {
			this.percent = percent;
		}

		public int getMin() {
			return min;
		}

		public void setMin(int min) {
			this.min = min;
		}
		
		
	}
	

}

