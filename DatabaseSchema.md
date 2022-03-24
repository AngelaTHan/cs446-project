**User**
```
id
image: url
Description
List of my own post id
List of collected post id
List of followers: arr(User.id)
List of following: arr(User.id)
#like: num
```

**RecipePost** 
```
User id: string
User name: string
Id : string
Title : sting
Description: string
coverPageImage url: string
List of image URLs: arr(string)
location : string
List of (Ingredient, amount): arr(tuple(string, num))
List of steps: arr(string)
#likes: num
List of comment ids: arr(string username, string content, timestamp)
Collect: boolean
```

**Comments** 
```
User Id: string
Id: string
Content: string
Time: datetime
```

