export interface AnswerListItem {
  nickName: string;
  answerVoteCount: number;
  profileImage:string;
  content: string;
  createdAt: string;
}

export interface Tag {
  tagId: number;
  tagName: string;
  tagContent: string;
}

export interface DummyData {
  questionId: number;
  title: string;
  questionVoteCount: number;
  content: string;
  tags: Tag[];
  answerList: AnswerListItem[];
  profileImage:string;
  nickName: string;
  createdAt: string;
}

export const detailData: DummyData[] = [
  {
    "questionId": 50,
    "title": "How to design a good JWT authentication filter",
    "questionVoteCount": 9,
    "content": "질문 내용입니다~~ 모르겠어잉",
    "tags": [
      {
          "tagId": 1,
          "tagName": "java",
          "tagContent": "Java is a high-level object-oriented programming language. Use this tag when you're having problems using or understanding the language itself. This tag is frequently used alongside other tags for libraries and/or frameworks used by Java developers."

      },
      {
          "tagId":3,
          "tagName": "spring",
          "tagContent": "spring blah blah"
          
      }
    ],
    "answerList": [
      {
        "nickName": "kimcoding",
        "answerVoteCount": 10,
        "profileImage": "/images/profile.jpg",
        "content": "이렇게 해보세요~!",
        "createdAt": "2023-06-15 16:18:14"
      }
    ],
    "profileImage": "/images/profile.jpg",
    "nickName": "dumbpotato24",
    "createdAt": "2023-06-15 16:18:14"
  },
  {
    "questionId": 49,
    "title": "merge sort program in java",
    "questionVoteCount": 30,
    "content": "I do not know everything",
    "tags": [
      {
          "tagId": 1,
          "tagName": "java",
          "tagContent": "Java is a high-level object-oriented programming language. Use this tag when you're having problems using or understanding the language itself. This tag is frequently used alongside other tags for libraries and/or frameworks used by Java developers."

      },
      {
          "tagId":3,
          "tagName": "spring",
          "tagContent": "spring blah blah"
          
      }
    ],
    "answerList": [
      {
        "nickName": "kimcoding",
        "answerVoteCount": 5,
        "profileImage": "/images/profile.jpg",
        "content": "i don't know too.<p></p><pre class='ql-syntax' spellcheck='false'>let num = 10;</pre>",
        "createdAt": "2023-06-15 16:18:14"
      },
      {
        "nickName": "kimcoding",
        "answerVoteCount": 7,
        "profileImage": "/images/profile.jpg",
        "content": "hello my name is <h1>hyesoo</h1>",
        "createdAt": "2023-06-15 16:18:14"
      }
    ],
    "profileImage": "/images/profile.jpg",
    "nickName": "dumbpotato24",
    "createdAt": "2023-06-16 12:18:00"
  },
  {
    "questionId": 48,
    "title": "배고파요.",
    "questionVoteCount": 30,
    "content": "배고픈데 저메추해주세요",
    "tags": [
      {
          "tagId": 1,
          "tagName": "java",
          "tagContent": "Java is a high-level object-oriented programming language. Use this tag when you're having problems using or understanding the language itself. This tag is frequently used alongside other tags for libraries and/or frameworks used by Java developers."

      }
    ],
    "answerList": [],
    "profileImage": "/images/profile.jpg",
    "nickName": "dumbpotato24",
    "createdAt": "2023-06-16 12:18:00"
  }
];