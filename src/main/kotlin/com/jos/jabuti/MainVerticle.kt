package com.jos.jabuti

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {

    val router = Router.router(vertx)

    router.route().path("/").handler { ctx ->
      ctx.end("Jabuti OIDC server")
    }

    router.route().path("/.well-known/openid-configuration").handler{ctx ->
      ctx.json(
        json {
          obj(
            "name" to "Pedro",
            "isBeaultiful" to true
          )
        }
      )
    }

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(8888) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 8888")
        } else {
          startPromise.fail(http.cause());
        }
      }
  }
}
