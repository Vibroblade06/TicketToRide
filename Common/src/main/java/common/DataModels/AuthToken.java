package common.DataModels;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class AuthToken implements Serializable
{
  private String token;
  private long generationTime;
  private static Set<String> validTokens = new HashSet<>();

  public AuthToken()
  {
    this.token = generateNewtoken();
    validTokens.add(this.token);
    this.generationTime = System.currentTimeMillis();
  }

  public static boolean isValidToken(String token)
  {
    if(validTokens.contains(token))
    {
      return true;
    } else
    {
      return false;
    }
  }

  public static TimerTask generateTimeout(String authtoken)
  {
    return new Timeout(authtoken);
  }

  public String gettoken()
  {
    return token;
  }

  public void settoken(String token)
  {
    this.token = token;
  }

  private String generateNewtoken()
  {

    StringBuilder token = new StringBuilder();
    token.setLength(0);
    token.append(UUID.randomUUID().toString());
    token.deleteCharAt(23);
    token.deleteCharAt(18);
    token.deleteCharAt(13);
    token.deleteCharAt(8);
    return token.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AuthToken)) return false;

    AuthToken authToken = (AuthToken) o;

    if (generationTime != authToken.generationTime) return false;
    return token != null ? token.equals(authToken.token) : authToken.token == null;
  }

  @Override
  public int hashCode() {
    int result = token != null ? token.hashCode() : 0;
    result = 31 * result + (int) (generationTime ^ (generationTime >>> 32));
    return result;
  }

  static class Timeout extends TimerTask
  {
    private final String authtoken;

    Timeout(String authtoken)
    {
      this.authtoken = authtoken;
    }

    @Override
    public void run()
    {
      validTokens.remove(this.authtoken);
    }
  }

}
