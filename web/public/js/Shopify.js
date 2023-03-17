var Shopify = Shopify || {};
Shopify.shop = "materialize-themes.myshopify.com";
Shopify.locale = "en";
Shopify.currency = {"active":"USD","rate":"1.0"};
Shopify.country = "US";
Shopify.theme = {"name":"debut","id":133945025,"theme_store_id":796,"role":"main"};
Shopify.theme.handle = "null";
Shopify.theme.style = {"id":null,"handle":null};
Shopify.cdnHost = "cdn.shopify.com";
Shopify.routes = Shopify.routes || {};
Shopify.routes.root = "/";

!function(o){(o.Shopify=o.Shopify||{}).modules=!0}(window);

!function(o){function n(){var o=[];function n(){o.push(Array.prototype.slice.apply(arguments))}return n.q=o,n}var t=o.Shopify=o.Shopify||{};t.loadFeatures=n(),t.autoloadFeatures=n()}(window);

window.ShopifyPay = window.ShopifyPay || {};
window.ShopifyPay.apiHost = "shop.app\/pay";

var __st={"a":17758583,"offset":-28800,"reqid":"961daa6e-b243-4bab-9062-bccec46027e6","pageurl":"themes.materializecss.com\/pages\/demo","s":"pages-199716225","u":"03362fab4cb1","p":"page","rtyp":"page","rid":199716225};

window.ShopifyPaypalV4VisibilityTracking = true;

!function(o){o.addEventListener("DOMContentLoaded",function(){window.Shopify=window.Shopify||{},window.Shopify.recaptchaV3=window.Shopify.recaptchaV3||{siteKey:"6LcCR2cUAAAAANS1Gpq_mDIJ2pQuJphsSQaUEuc9"};var t=['form[action*="/contact"] input[name="form_type"][value="contact"]','form[action*="/comments"] input[name="form_type"][value="new_comment"]','form[action*="/account"] input[name="form_type"][value="customer_login"]','form[action*="/account"] input[name="form_type"][value="recover_customer_password"]','form[action*="/account"] input[name="form_type"][value="create_customer"]','form[action*="/contact"] input[name="form_type"][value="customer"]'].join(",");function n(e){e=e.target;null==e||null!=(e=function e(t,n){if(null==t.parentElement)return null;if("FORM"!=t.parentElement.tagName)return e(t.parentElement,n);for(var o=t.parentElement.action,r=0;r<n.length;r++)if(-1!==o.indexOf(n[r]))return t.parentElement;return null}(e,["/contact","/comments","/account"]))&&null!=e.querySelector(t)&&((e=o.createElement("script")).setAttribute("src","https://cdn.shopify.com/shopifycloud/storefront-recaptcha-v3/v0.6/index.js"),o.body.appendChild(e),o.removeEventListener("focus",n,!0),o.removeEventListener("change",n,!0),o.removeEventListener("click",n,!0))}o.addEventListener("click",n,!0),o.addEventListener("change",n,!0),o.addEventListener("focus",n,!0)})}(document)

window.performance && window.performance.mark && window.performance.mark('shopify.content_for_header.end');

(function(){if ("sendBeacon" in navigator && "performance" in window) {var session_token = document.cookie.match(/_shopify_s=([^;]*)/);function handle_abandonment_event(e) {var entries = performance.getEntries().filter(function(entry) {return /monorail-edge.shopifysvc.com/.test(entry.name);});if (!window.abandonment_tracked && entries.length === 0) {window.abandonment_tracked = true;var currentMs = Date.now();var navigation_start = performance.timing.navigationStart;var payload = {shop_id: 17758583,url: window.location.href,navigation_start,duration: currentMs - navigation_start,session_token: session_token && session_token.length === 2 ? session_token[1] : "",page_type: "page"};window.navigator.sendBeacon("https://monorail-edge.shopifysvc.com/v1/produce", JSON.stringify({schema_id: "online_store_buyer_site_abandonment/1.1",payload: payload,metadata: {event_created_at_ms: currentMs,event_sent_at_ms: currentMs}}));}}window.addEventListener('pagehide', handle_abandonment_event);}}());

window.ShopifyAnalytics = window.ShopifyAnalytics || {};
window.ShopifyAnalytics.meta = window.ShopifyAnalytics.meta || {};
window.ShopifyAnalytics.meta.currency = 'USD';
var meta = {"page":{"pageType":"page","resourceType":"page","resourceId":199716225}};
for (var attr in meta) {
  window.ShopifyAnalytics.meta[attr] = meta[attr];
}

window.ShopifyAnalytics.merchantGoogleAnalytics = function() {};

(window.gaDevIds = window.gaDevIds || []).push('BwiEti');


        (function () {
            var customDocumentWrite = function (content) {
                var jquery = null;

                if (window.jQuery) {
                    jquery = window.jQuery;
                } else if (window.Checkout && window.Checkout.$) {
                    jquery = window.Checkout.$;
                }

                if (jquery) {
                    jquery('body').append(content);
                }
            };

            var hasLoggedConversion = function (token) {
                if (token) {
                    return document.cookie.indexOf('loggedConversion=' + token) !== -1;
                }
                return false;
            }

            var setCookieIfConversion = function (token) {
                if (token) {
                    var twoMonthsFromNow = new Date(Date.now());
                    twoMonthsFromNow.setMonth(twoMonthsFromNow.getMonth() + 2);

                    document.cookie = 'loggedConversion=' + token + '; expires=' + twoMonthsFromNow;
                }
            }

            var trekkie = window.ShopifyAnalytics.lib = window.trekkie = window.trekkie || [];
            if (trekkie.integrations) {
                return;
            }
            trekkie.methods = [
                'identify',
                'page',
                'ready',
                'track',
                'trackForm',
                'trackLink'
            ];
            trekkie.factory = function (method) {
                return function () {
                    var args = Array.prototype.slice.call(arguments);
                    args.unshift(method);
                    trekkie.push(args);
                    return trekkie;
                };
            };
            for (var i = 0; i < trekkie.methods.length; i++) {
                var key = trekkie.methods[i];
                trekkie[key] = trekkie.factory(key);
            }
            trekkie.load = function (config) {
                trekkie.config = config || {};
                trekkie.config.initialDocumentCookie = document.cookie;
                var first = document.getElementsByTagName('script')[0];
                var script = document.createElement('script');
                script.type = 'text/javascript';
                script.onerror = function (e) {
                    var scriptFallback = document.createElement('script');
                    scriptFallback.type = 'text/javascript';
                    scriptFallback.onerror = function (error) {
                        var Monorail = {
                            produce: function produce(monorailDomain, schemaId, payload) {
                                var currentMs = new Date().getTime();
                                var event = {
                                    schema_id: schemaId,
                                    payload: payload,
                                    metadata: {
                                        event_created_at_ms: currentMs,
                                        event_sent_at_ms: currentMs
                                    }
                                };
                                return Monorail.sendRequest("https://" + monorailDomain + "/v1/produce", JSON.stringify(event));
                            },
                            sendRequest: function sendRequest(endpointUrl, payload) {
                                // Try the sendBeacon API
                                if (window && window.navigator && typeof window.navigator.sendBeacon === 'function' && typeof window.Blob === 'function' && !Monorail.isIos12()) {
                                    var blobData = new window.Blob([payload], {
                                        type: 'text/plain'
                                    });

                                    if (window.navigator.sendBeacon(endpointUrl, blobData)) {
                                        return true;
                                    } // sendBeacon was not successful

                                } // XHR beacon   

                                var xhr = new XMLHttpRequest();

                                try {
                                    xhr.open('POST', endpointUrl);
                                    xhr.setRequestHeader('Content-Type', 'text/plain');
                                    xhr.send(payload);
                                } catch (e) {
                                    console.log(e);
                                }

                                return false;
                            },
                            isIos12: function isIos12() {
                                return window.navigator.userAgent.lastIndexOf('iPhone; CPU iPhone OS 12_') !== -1 || window.navigator.userAgent.lastIndexOf('iPad; CPU OS 12_') !== -1;
                            }
                        };
                        Monorail.produce('monorail-edge.shopifysvc.com',
                                'trekkie_storefront_load_errors/1.1',
                                {shop_id: 17758583,
                                    theme_id: 133945025,
                                    app_name: "storefront",
                                    context_url: window.location.href,
                                    source_url: "https://cdn.shopify.com/s/trekkie.storefront.fc189574be3bbc8f725b1c742b8674dddab0dd9a.min.js"});

                    };
                    scriptFallback.async = true;
                    scriptFallback.src = 'https://cdn.shopify.com/s/trekkie.storefront.fc189574be3bbc8f725b1c742b8674dddab0dd9a.min.js';
                    first.parentNode.insertBefore(scriptFallback, first);
                };
                script.async = true;
                script.src = 'https://cdn.shopify.com/s/trekkie.storefront.fc189574be3bbc8f725b1c742b8674dddab0dd9a.min.js';
                first.parentNode.insertBefore(script, first);
            };
            trekkie.load(
                    {"Trekkie": {"appName": "storefront", "development": false, "defaultAttributes": {"shopId": 17758583, "isMerchantRequest": null, "themeId": 133945025, "themeCityHash": "1670185919760860438", "contentLanguage": "en", "currency": "USD"}, "isServerSideCookieWritingEnabled": true, "monorailRegion": "shop_domain"}, "Google Analytics": {"trackingId": "UA-56218128-1", "domain": "auto", "siteSpeedSampleRate": "10", "enhancedEcommerce": true, "doubleClick": true, "includeSearch": true}, "Session Attribution": {}, "S2S": {"facebookCapiEnabled": false, "source": "trekkie-storefront-renderer"}}
            );

            var loaded = false;
            trekkie.ready(function () {
                if (loaded)
                    return;
                loaded = true;

                window.ShopifyAnalytics.lib = window.trekkie;

                ga('require', 'linker');
                function addListener(element, type, callback) {
                    if (element.addEventListener) {
                        element.addEventListener(type, callback);
                    } else if (element.attachEvent) {
                        element.attachEvent('on' + type, callback);
                    }
                }
                function decorate(event) {
                    event = event || window.event;
                    var target = event.target || event.srcElement;
                    if (target && (target.getAttribute('action') || target.getAttribute('href'))) {
                        ga(function (tracker) {
                            var linkerParam = tracker.get('linkerParam');
                            document.cookie = '_shopify_ga=' + linkerParam + '; ' + 'path=/';
                        });
                    }
                }
                addListener(window, 'load', function () {
                    for (var i = 0; i < document.forms.length; i++) {
                        var action = document.forms[i].getAttribute('action');
                        if (action && action.indexOf('/cart') >= 0) {
                            addListener(document.forms[i], 'submit', decorate);
                        }
                    }
                    for (var i = 0; i < document.links.length; i++) {
                        var href = document.links[i].getAttribute('href');
                        if (href && href.indexOf('/checkout') >= 0) {
                            addListener(document.links[i], 'click', decorate);
                        }
                    }
                });


                var originalDocumentWrite = document.write;
                document.write = customDocumentWrite;
                try {
                    window.ShopifyAnalytics.merchantGoogleAnalytics.call(this);
                } catch (error) {
                }
                ;
                document.write = originalDocumentWrite;

                window.ShopifyAnalytics.lib.page(null, {"pageType": "page", "resourceType": "page", "resourceId": 199716225});

                var match = window.location.pathname.match(/checkouts\/(.+)\/(thank_you|post_purchase)/)
                var token = match ? match[1] : undefined;
                if (!hasLoggedConversion(token)) {
                    setCookieIfConversion(token);

                }
            });


            var eventsListenerScript = document.createElement('script');
            eventsListenerScript.async = true;
            eventsListenerScript.src = "//cdn.shopify.com/shopifycloud/shopify/assets/shop_events_listener-65cd0ba3fcd81a1df33f2510ec5bcf8c0e0958653b50e3965ec972dd638ee13f.js";
            document.getElementsByTagName('head')[0].appendChild(eventsListenerScript);

        })();
        
        
 (function () {
            if (window.BOOMR && (window.BOOMR.version || window.BOOMR.snippetExecuted)) {
                return;
            }
            window.BOOMR = window.BOOMR || {};
            window.BOOMR.snippetStart = new Date().getTime();
            window.BOOMR.snippetExecuted = true;
            window.BOOMR.snippetVersion = 12;
            window.BOOMR.application = "storefront-renderer";
            window.BOOMR.themeName = "Debut";
            window.BOOMR.themeVersion = "1.0.1";
            window.BOOMR.shopId = 17758583;
            window.BOOMR.themeId = 133945025;
            window.BOOMR.renderRegion = "gcp-us-east1";
            window.BOOMR.url =
                    "https://cdn.shopify.com/shopifycloud/boomerang/shopify-boomerang-1.0.0.min.js";
            var where = document.currentScript || document.getElementsByTagName("script")[0];
            var parentNode = where.parentNode;
            var promoted = false;
            var LOADER_TIMEOUT = 3000;
            function promote() {
                if (promoted) {
                    return;
                }
                var script = document.createElement("script");
                script.id = "boomr-scr-as";
                script.src = window.BOOMR.url;
                script.async = true;
                parentNode.appendChild(script);
                promoted = true;
            }
            function iframeLoader(wasFallback) {
                promoted = true;
                var dom, bootstrap, iframe, iframeStyle;
                var doc = document;
                var win = window;
                window.BOOMR.snippetMethod = wasFallback ? "if" : "i";
                bootstrap = function (parent, scriptId) {
                    var script = doc.createElement("script");
                    script.id = scriptId || "boomr-if-as";
                    script.src = window.BOOMR.url;
                    BOOMR_lstart = new Date().getTime();
                    parent = parent || doc.body;
                    parent.appendChild(script);
                };
                if (!window.addEventListener && window.attachEvent && navigator.userAgent.match(/MSIE [67]./)) {
                    window.BOOMR.snippetMethod = "s";
                    bootstrap(parentNode, "boomr-async");
                    return;
                }
                iframe = document.createElement("IFRAME");
                iframe.src = "about:blank";
                iframe.title = "";
                iframe.role = "presentation";
                iframe.loading = "eager";
                iframeStyle = (iframe.frameElement || iframe).style;
                iframeStyle.width = 0;
                iframeStyle.height = 0;
                iframeStyle.border = 0;
                iframeStyle.display = "none";
                parentNode.appendChild(iframe);
                try {
                    win = iframe.contentWindow;
                    doc = win.document.open();
                } catch (e) {
                    dom = document.domain;
                    iframe.src = "javascript:var d=document.open();d.domain='" + dom + "';void(0);";
                    win = iframe.contentWindow;
                    doc = win.document.open();
                }
                if (dom) {
                    doc._boomrl = function () {
                        this.domain = dom;
                        bootstrap();
                    };
                    doc.write("<body onload='document._boomrl();'>");
                } else {
                    win._boomrl = function () {
                        bootstrap();
                    };
                    if (win.addEventListener) {
                        win.addEventListener("load", win._boomrl, false);
                    } else if (win.attachEvent) {
                        win.attachEvent("onload", win._boomrl);
                    }
                }
                doc.close();
            }
            var link = document.createElement("link");
            if (link.relList &&
                    typeof link.relList.supports === "function" &&
                    link.relList.supports("preload") &&
                    ("as" in link)) {
                window.BOOMR.snippetMethod = "p";
                link.href = window.BOOMR.url;
                link.rel = "preload";
                link.as = "script";
                link.addEventListener("load", promote);
                link.addEventListener("error", function () {
                    iframeLoader(true);
                });
                setTimeout(function () {
                    if (!promoted) {
                        iframeLoader(true);
                    }
                }, LOADER_TIMEOUT);
                BOOMR_lstart = new Date().getTime();
                parentNode.appendChild(link);
            } else {
                iframeLoader(false);
            }
            function boomerangSaveLoadTime(e) {
                window.BOOMR_onload = (e && e.timeStamp) || new Date().getTime();
            }
            if (window.addEventListener) {
                window.addEventListener("load", boomerangSaveLoadTime, false);
            } else if (window.attachEvent) {
                window.attachEvent("onload", boomerangSaveLoadTime);
            }
            if (document.addEventListener) {
                document.addEventListener("onBoomerangLoaded", function (e) {
                    e.detail.BOOMR.init({
                        ResourceTiming: {
                            enabled: true,
                            trackedResourceTypes: ["script", "img", "css"]
                        },
                    });
                    e.detail.BOOMR.t_end = new Date().getTime();
                });
            } else if (document.attachEvent) {
                document.attachEvent("onpropertychange", function (e) {
                    if (!e)
                        e = event;
                    if (e.propertyName === "onBoomerangLoaded") {
                        e.detail.BOOMR.init({
                            ResourceTiming: {
                                enabled: true,
                                trackedResourceTypes: ["script", "img", "css"]
                            },
                        });
                        e.detail.BOOMR.t_end = new Date().getTime();
                    }
                });
            }
        })();
        
(function e(e, n, a, o, t) {
                e && (window.Shopify = window.Shopify || {}, window.Shopify.analytics = window.Shopify.analytics || {}, window.Shopify.analytics.replayQueue = [], window.Shopify.analytics.publish = function (e, n, a) {
                    window.Shopify.analytics.replayQueue.push([e, n, a])
                });
                var r, i, s, l, d, c, p, u, f = a + "/" + o + "." + function () {
                    var e = "legacy", n = "unknown", a = null, o = navigator.userAgent.match(/(Firefox|Chrome)\/(\d+)/i), t = navigator.userAgent.match(/(Edg)\/(\d+)/i), r = navigator.userAgent.match(/(Version)\/(\d+)(.+)(Safari)\/(\d+)/i);
                    r ? (n = "safari", a = parseInt(r[2], 10)) : t ? (n = "edge", a = parseInt(t[2], 10)) : o && (n = o[1].toLocaleLowerCase(), a = parseInt(o[2], 10));
                    var i = {chrome: 60, firefox: 55, safari: 11, edge: 80}[n];
                    return void 0 !== i && null !== a && i <= a && (e = "modern"), e
                }() + ".js";
                r = {src: f, async: !0, onload: function () {
                        if (e) {
                            var a = window.webPixelsManager.init(e);
                            n(a), window.Shopify.analytics.replayQueue.forEach((function (e) {
                                a.publishCustomEvent(e[0], e[1], e[2])
                            })), window.Shopify.analytics.replayQueue = [], window.Shopify.analytics.publish = a.publishCustomEvent
                        }
                    }, onerror: function () {
                        var n = (e.storefrontBaseUrl ? e.storefrontBaseUrl.replace(/\/$/, "") : self.location.origin) + "/.well-known/shopify/monorail/unstable/produce_batch", a = JSON.stringify({metadata: {event_sent_at_ms: (new Date).getTime()}, events: [{schema_id: "web_pixels_manager_load/2.0", payload: {version: t || "latest", page_url: self.location.href, status: "failed", error_msg: f + " has failed to load"}, metadata: {event_created_at_ms: (new Date).getTime()}}]});
                        try {
                            if (self.navigator.sendBeacon.bind(self.navigator)(n, a))
                                return!0
                        } catch (e) {
                        }
                        const o = new XMLHttpRequest;
                        try {
                            return o.open("POST", n, !0), o.setRequestHeader("Content-Type", "text/plain"), o.send(a), !0
                        } catch (e) {
                            console && console.warn && console.warn("[Web Pixels Manager] Got an unhandled error while logging a load error.")
                        }
                        return!1
                    }}, i = document.createElement("script"), s = r.src, l = r.async || !0, d = r.onload, c = r.onerror, p = document.head, u = document.body, i.async = l, i.src = s, d && i.addEventListener("load", d), c && i.addEventListener("error", c), p ? p.appendChild(i) : u ? u.appendChild(i) : console.error("Did not find a head or body element to append the script")
            })({shopId: 17758583, storefrontBaseUrl: "https://themes.materializecss.com", cdnBaseUrl: "https://cdn.shopify.com", surface: "storefront-renderer", enabledBetaFlags: ["web_pixels_use_shop_domain_monorail_endpoint"], webPixelExtensionBaseUrl: "https://cdn.shopify.com", webPixelsConfigList: [], initData: {"customer": null, "cart": null, "productVariants": [], "checkout": null}, }, function pageEvents(webPixelsManagerAPI) {
                webPixelsManagerAPI.publish("page_viewed");
            }, "https://cdn.shopify.com/shopifycloud/web-pixels-manager/0.0.186", "browser", "0.0.186");
            
            

