# jTwitch
OAuth 2 Twitch API wrapper written in Java.

It is simple to use and should not require a lot of effort to get up and running.

Still in the early stages - but what is already implemented should work; checkout the samples.

## Usage
Authenticate with OAuth 2 and obtain a token:
```java
Properties prop = new Properties();
prop.load(new FileInputStream("jtwitch.properties"));

final String redirectUri = "http://localhost:8000/";
final String clientId = prop.getProperty("twitch.clientId");
final String clientSecret = prop.getProperty("twitch.clientSecret");

OAuth2Authenticate oaa = new OAuth2Authenticate(clientId, clientSecret, redirectUri);

// sampleUse is key used for storing token
TwitchToken twitchToken = oaa.authenticate("sampleUse");
```
Create an API instance:
```java
TwitchFactory factory = new TwitchFactory.Builder()
    .setClientId(clientId)
    .setAccessToken(twitchToken.getAccessToken())
    .build();

TwitchUsersApi usersApi = (TwitchUsersApi) factory.getInstance(TwitchFactory.API.Users);
```
Execute a request:
```java
UserFollows userFollows = usersApi.getChannelsFollowedByUser(userId);

System.out.println("User is following:");

StringBuilder sb = new StringBuilder();

for (UserFollow f : userFollows.getFollows()) {
    sb.append("Channel '").append(f.getChannel().getDisplayName())
        .append("' which has ").append(f.getChannel().getFollowers())
        .append(" followers!\n");
}

String results = sb.toString();
System.out.println(results);
```