## Table of Contents
###### Get Data from Server
```
	logIn(str: username, str: password)
  	getProfile(str: username)
	viewPost(str: username, str: article_id)
	readComments(str: article_id)
	createPage(str: username)
	getHomePage(str: username)
	viewNotification(str: username)
```
	
###### Save Data to Server
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
## Details
**Login –logIn(str: username, str: password)**
```
{
  Username:xxx,
  Password:xxx,
}
```

**My profile, View A Post -> View Author Profile – getProfile(str: username)**
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

**My Profile –>View A Post – viewPost(str: usrname, str: article id)** 
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

**View A Post –>Comment – readComments(str: article_id)**
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

**Create New Page -> createPage(str: username)**
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

**Home Page – getHomePage(str: username)**
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
  
**Home Page –> View Notifications – viewNotification(str: username)**
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

**Registration –> check whether a username has been used – checkName(str: name)**
```
{
  canUse: true / false
}
```

**Registration -> save all information and create new account – createAccount(str: name, str: password, image: abe.jpg, str: description)**
```
{
  Account created: true / false
}
```

**View A Post -> like the post - likePost(str: article_id, str: username)**
```
{
  Action succussed: true/false,
  Post likes: number
}
```

**View A Post –> collect the post – collectPost(str: article_id, str: username)**
```
{
  Action succussed: true/false,
  Post collects: number
}
```

**View Post Comments -> make a comment – addComment(str: username, str: article_id, str: comment)**
```
{
  Action succussed: true / false
}
```

**View A Post -> follow the author – followAuthor(str:username, str: authorName)**
```
{
  Action succussed: true / false
}
```

**Create New Post –> new post – createNewPost(str: username, str: title, str: summary, str:location, Arrary of [(str, str)]: ingredients, Array of [(str, image)]: steps)**
```
{
  Action succussed: true / false,
  Article id: number
}
```

**Create New Post –> save post – saveCurPost(str: username, str: title, str: summary, str:location, Arrary of [(str, str)]: ingredients, Array of [(str, image)]: steps)**
```
{
  Action succussed: true / false
}
```
