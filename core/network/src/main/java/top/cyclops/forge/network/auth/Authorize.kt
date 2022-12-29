package top.cyclops.forge.network.auth

import top.cyclops.forge.network.auth.Authority

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authorize(val authority: Authority = Authority.Required)