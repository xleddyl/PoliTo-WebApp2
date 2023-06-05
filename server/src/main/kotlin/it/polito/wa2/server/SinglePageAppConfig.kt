package it.polito.wa2.server

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class ReactAppController {
    @RequestMapping(value = ["/", "/{x:[\\w\\-]+}"])
    fun getIndex(request: HttpServletRequest?): String {
        return "/index.html"
    }
}

/*
@Configuration
class SinglePageAppConfig : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .resourceChain(false)
            .addResolver(PushStateResourceResolver())
    }

    private inner class PushStateResourceResolver : ResourceResolver {
        private val index: Resource = ClassPathResource("/static/index.html")
        private val handledExtensions: List<String> = mutableListOf(
            "html",
            "js",
            "json",
            "csv",
            "css",
            "png",
            "svg",
            "eot",
            "ttf",
            "woff",
            "appcache",
            "jpg",
            "jpeg",
            "gif",
            "ico"
        )
        private val ignoredPaths: List<String> = mutableListOf("api")
        override fun resolveResource(
            @Nullable request: HttpServletRequest?,
            requestPath: String,
            locations: MutableList<out Resource>,
            chain: ResourceResolverChain
        ): Resource? {
            return resolve(requestPath, locations)
        }

        override fun resolveUrlPath(
            resourcePath: String,
            locations: MutableList<out Resource>,
            chain: ResourceResolverChain
        ): String? {
            val resolvedResource: Resource = resolve(resourcePath, locations) ?: return null
            return try {
                resolvedResource.getURL().toString()
            } catch (e: IOException) {
                resolvedResource.getFilename()
            }
        }

        private fun resolve(requestPath: String, locations: List<Resource?>): Resource? {
            if (isIgnored(requestPath)) {
                return null
            }
            return if (isHandled(requestPath)) ({
                locations.stream()
                    .map<Any?> { loc: Resource? -> createRelative(loc, requestPath) }
                    .filter { resource: Any? -> resource != null && resource.exists() }
                    .findFirst()
                    .orElseGet(null)
            }) as Resource? else index
        }

        private fun createRelative(resource: Resource?, relativePath: String): Resource? {
            if (resource != null) {
                return try {
                    resource.createRelative(relativePath)
                } catch (e: IOException) {
                    null
                }
            }
            return null
        }

        private fun isIgnored(path: String): Boolean {
            return ignoredPaths.contains(path)
        }

        private fun isHandled(path: String): Boolean {
            val extension: String? = StringUtils.getFilenameExtension(path)
            return handledExtensions.stream().anyMatch { ext: String -> ext == extension }
        }
    }
}

     */