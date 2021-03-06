jTwitch
=======
OAuth 2 Twitch API wrapper written in Java.

It is simple to use and should not require a lot of effort to get up and running.

Still in the early stages - but what is already implemented should work; checkout the examples.

### Table of Contents
- [Progress](#progress)
- [Usage](#usage)
- [Examples](#examples)
- [Download](#download)
- [License](#license)

Progress
--------
In progress development will be made on the develop branch until finished and then merge into master.

Twitch API checklist:
 - Bits
 - Channel Feed
 - Channels
 - Chat
 - Clips
 - Collections
 - Communities
 - Games
 - Ingests
 - Search
 - ~~Streams~~
 - ~~Teams~~
 - ~~Users~~
 - ~~Videos~~

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

TwitchUsersApi usersApi = factory.getInstance(TwitchUsersApi.class).build(factory.getClient());
```
Execute a request:
```java
List<UserFollow> userFollowList = usersApi.getChannelsFollowedByUser(userId);
```
Examples
--------
There are a few samples in `samples/src/main`.

Download
--------
You can package your own JAR file using maven: `mvn package`.

License
-------
See LICENSE file.