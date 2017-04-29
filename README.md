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
Create an API wrapper instance:
```java
TwitchFactory factory = new TwitchFactory.Builder()
    .setClientId(clientId)
    .setAccessToken(twitchToken.getAccessToken())
    .build();

TwitchUsersApi usersApi = (TwitchUsersApi) factory.getInstance(TwitchFactory.API.Users);
```
Execute a request:
```java
// userId is id of user associated with token - in other words, your Twitch account id
UserFollows userFollows = usersApi.getChannelsFollowedByUser(userId);
```
## Examples
There are a few samples in `samples/src/main`.