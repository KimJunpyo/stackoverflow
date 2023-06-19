import React from 'react';
import {
  ONE_DAY_MILLISECOND,
  ONE_HOUR_MILLISECOND,
  ONE_MINUTE_MILLISECOND,
  ONE_SECOND_MILLISECOND,
} from '../common/data/ConstantValue';
import { QuestionItemData } from '../pages/Main';
import {
  QuestionItemContainer,
  QuestionItemActive,
  QuestionVote,
  QuestionDataContainer,
  QuestionItemTitle,
  QuestionBodyContainer,
  QuestionTagList,
  QuestionTagName,
  QuestionUserContainer,
  QuestionUserProfile,
  QuestionUserName,
  QuestionAnswer,
} from '../style/QuestionItem.styled';

type QuestionItemProps = {
  questionProps: QuestionItemData;
};

function QuestionItem({ questionProps }: QuestionItemProps) {
  const { votes, answer, title, tags, profileImage, nickName, createdAt } =
    questionProps;

  const changeDate = (date: string) => {
    const nowDate = Date.now();
    const oldDate = Date.parse(date);
    let dateDiff = nowDate - oldDate;

    if (dateDiff < ONE_SECOND_MILLISECOND) {
      return 'now';
    }
    if (dateDiff < ONE_MINUTE_MILLISECOND) {
      dateDiff = Math.floor(dateDiff / ONE_SECOND_MILLISECOND);
      return `${dateDiff} sec ago`;
    }
    if (dateDiff < ONE_HOUR_MILLISECOND) {
      dateDiff = Math.floor(dateDiff / ONE_MINUTE_MILLISECOND);
      return `${dateDiff} min ago`;
    }
    if (dateDiff < ONE_DAY_MILLISECOND) {
      dateDiff = Math.floor(dateDiff / ONE_HOUR_MILLISECOND);
      return `${dateDiff} hour ago`;
    }
    dateDiff = Math.floor(dateDiff / ONE_DAY_MILLISECOND);

    return `${dateDiff} day ago`;
  };

  return (
    <QuestionItemContainer>
      <QuestionItemActive>
        <QuestionVote>{votes} votes</QuestionVote>
        <QuestionAnswer
          className={
            answer ? `border-green border text-green` : `text-[#6A737C]`
          }
        >
          {answer} answers
        </QuestionAnswer>
      </QuestionItemActive>
      <QuestionDataContainer>
        <QuestionItemTitle>{title}</QuestionItemTitle>
        <QuestionBodyContainer>
          <ul>
            {tags.map(e => (
              <QuestionTagList key={e.tagId}>
                <QuestionTagName>{e.tagName}</QuestionTagName>
              </QuestionTagList>
            ))}
          </ul>
          <QuestionUserContainer>
            <QuestionUserProfile src={profileImage} alt="프로필 사진" />
            <QuestionUserName>{nickName}</QuestionUserName>
            <time className="text-[#6A737C]">{changeDate(createdAt)}</time>
          </QuestionUserContainer>
        </QuestionBodyContainer>
      </QuestionDataContainer>
    </QuestionItemContainer>
  );
}

export default QuestionItem;
