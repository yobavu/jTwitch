jTwitch
=======
OAuth 2 Twitch API wrapper written in Java.

It is simple to use and should not require a lot of effort to get up and running.

Still in the early stages - but what is already implemented should work; checkout the samples.

Usage
-----
Authenticate with OAuth 2 and obtain a token:
```java
OAuth2Authenticate oaa = new OAuth2Authenticate(clientId, clientSecret, redirectUri, scopes);

// sampleUse is key used for serializing and storing token
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
UserFollowList userFollowList = usersApi.getChannelsFollowedByUser(userId);
```
Examples
--------
There are a few samples in `samples/src/main`.

Download
--------
The latest JAR is available on the release page.

License
-------
See LICENSE file.