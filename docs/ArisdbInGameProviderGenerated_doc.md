## aris.game.redis.set(key: string, value: string) -> string
## aris.game.redis.get(key: string) -> String
## aris.game.redis.lpush(key: string, value: string) -> number
## aris.game.redis.rpush(key: string, value: string) -> number
## aris.game.redis.lpop(key: string) -> String
## aris.game.redis.rpop(key: string) -> String
## aris.game.redis.sadd(key: string, value: string) -> number
## aris.game.redis.smembers(key: string) -> LuaStringSet
## aris.game.redis.subscribe(key: string, func: function)
```
 @param func (channel, message) -> void
```
## aris.game.redis.publish(key: string, value: string) -> number
## aris.game.mongo.collection(name: string) -> LuaMongoCollection


## LuaStringSet:size() -> number


## LuaStringSet:contains(value: string) -> boolean


## LuaStringSet:get(index: number) -> String


## LuaStringSet:to_table() -> Map


## LuaMongoCollection:insert(documentJson: string)


## LuaMongoCollection:find_one(queryJson: string) -> String


## LuaMongoCollection:find(queryJson: string) -> LuaDocumentList


## LuaMongoCollection:update(queryJson: string, updateJson: string) -> number


## LuaMongoCollection:delete(queryJson: string) -> number


## LuaMongoCollection:count(queryJson: string) -> number


## LuaMongoCollection:drop()


## LuaDocumentList:size() -> number


## LuaDocumentList:get(index: number) -> String


## LuaDocumentList:to_table() -> Map
