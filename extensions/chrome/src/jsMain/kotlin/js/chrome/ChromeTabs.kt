package js.chrome

external interface Tab {
    var id: Number?
        get() = definedExternally
        set(value) = definedExternally
    var index: Number
    var groupId: Number
    var windowId: Number
    var openerTabId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var selected: Boolean
    var highlighted: Boolean
    var active: Boolean
    var pinned: Boolean
    var audible: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var discarded: Boolean
    var autoDiscardable: Boolean

//    var mutedInfo: MutedInfo?
//        get() = definedExternally
//        set(value) = definedExternally
    var url: String?
        get() = definedExternally
        set(value) = definedExternally
    var pendingUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var favIconUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var status: String? /* "unloaded" | "loading" | "complete" */
        get() = definedExternally
        set(value) = definedExternally
    var incognito: Boolean
    var width: Number?
        get() = definedExternally
        set(value) = definedExternally
    var height: Number?
        get() = definedExternally
        set(value) = definedExternally
    var sessionId: String?
        get() = definedExternally
        set(value) = definedExternally
}

