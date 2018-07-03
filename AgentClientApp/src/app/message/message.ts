export class Message {
    constructor(
        public performative: any,
        public sender: any,
        public receivers: any[],
        public content: string,
        public replyTo?: any,
        public language?: string,
        public encoding?: string,
        public ontology?: string,
        public protocol?: string,
        public conversationId?: string,
        public replyWith?: string,
        public inReplyTo?: string,
        public replyBy?: number
    ){}
}
