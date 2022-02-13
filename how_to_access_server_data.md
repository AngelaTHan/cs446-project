# Functions to interact with the Database
This document includes all functions that the frontend (pages) can call to read / write from the database. They're roughly divided into two categories:
- Functions to read from the db
- Functions to write to db.

## Table of Contents
###### Functions to read from db
```
	logIn(str: username, str: password)
  	getProfile(str: username)
	viewPost(str: username, str: article_id)
	readComments(str: article_id)
	createPage(str: username)
	getHomePage(str: username)
	viewNotification(str: username)
```
	
###### Functions to write to db
```
	checkName(str: name)
	createAccount(str: name, str: password, image: abe.jpg, str: description)
	likePost(str: article_id, str: username)
	collectPost(str: article_id, str: username)
	addComment(str: username, str: article_id, str: comment)
	createNewPost(str: username, str: title, str: summary, str:location, Arrary of [(str, str)]: ingredients, Array of [(str, image)]: steps)
	saveCurPost(str: username, str: title, str: summary, str:location, Arrary of [(str, str)]: ingredients, Array of [(str, image)]: steps)
	followAuthor(str:username, str: authorName)
```

## Function details - Database Read Functions

**logIn(str: username, str: password)**
```
{
  Username:xxx,
  Password:xxx,
}
```

**getProfile(str: username)**
```
{
  Username:xxx,
  Description: “mhnafhawo“,
  Image: abc.jpg,
  Follower: 123,
  Following:213,
  Likes: 345,
  My past posts: [
    {
      post1_image:wf.jpg,
      post1_access_id:1819kkh33,
      post1_title: hahaha,
      post1_likes: 12,
      post1_collects: 3
    }, 
    {
      …
    }
    …
  ],
  My collected posts: [
    {
      post1_image:wf.jpg,
      post1_access_id:1819kkh33,
      post1_title: hahaha,
      post1_likes: 12,
      post1_collects: 3
    }, 
    {
      …
    }
    …
  ]
}
```

**viewPost(str: usrname, str: article id)** 
```
{
  My own post: true / false,
  Article id: 18h3n9snj,
  Title: swsf,
  Likes:123,
  Collects: 123,
  Location: swoaehgh,
  Author image: we.jpg,
  Author name: ehfto,
  Summary:jjjjj,
  Ingredients: [
    I1: (name, amount),
    I2: (name, amount),
    …
  ],
  Steps: [
    Step1: (string, image.jpg),
    Step2: (string, image.jpg),
    ….
  ],
  Comment number: 333
}
```

**readComments(str: article_id)**
```
{
  [
    Comment1: {
      User name: jjj,
      User image: jkk,
      Comment content: jkkjaki,
      Comment id: j8jhh77tf3
    },
    {
      …
    },
    …
  ]
}
```

**createPage(str: username)**
```
{
  Continue last edit: false 
}  
Or 
{
  Continue last edit: true,
  Title: jjj,
  Location: kkk,
  Summary: howh,
  Ingredients: [
    I1: (name, amount),
    …
  ],
  Steps: [
    Step1:(content, image.jpg),
    …
  ]
}
```

**getHomePage(str: username)**
```
{
  New notifications: true / false,
  Recommended posts: [
    Post1: {
      Title: ajb,
      Image: k.jpg,
      Likes: 12,
      Collects: 134,
      Summary: woefh
    },
    …
  ]
}
```
  
**viewNotification(str: username)**
```
{
  [
    N1: {
      Time: 2022-02-28 16:30:23,
      Title: new likes
      Content: A likes your post hh,
      Notification id: jju8ik89390h
    }
  ]
}
```

## Function details - Database Write Functions

**checkName(str: name)**
```
{
  canUse: true / false
}
```

**createAccount(str: name, str: password, image: abe.jpg, str: description)**
```
{
  Account created: true / false
}
```

**likePost(str: article_id, str: username)**
```
{
  Action succussed: true/false,
  Post likes: number
}
```

**collectPost(str: article_id, str: username)**
```
{
  Action succussed: true/false,
  Post collects: number
}
```

**addComment(str: username, str: article_id, str: comment)**
```
{
  Action succussed: true / false
}
```

**followAuthor(str:username, str: authorName)**
```
{
  Action succussed: true / false
}
```

**createNewPost(str: username, str: title, str: summary, str:location, Arrary of [(str, str)]: ingredients, Array of [(str, image)]: steps)**
```
{
  Action succussed: true / false,
  Article id: number
}
```

**saveCurPost(str: username, str: title, str: summary, str:location, Arrary of [(str, str)]: ingredients, Array of [(str, image)]: steps)**
```
{
  Action succussed: true / false
}
```
