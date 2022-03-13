import React from "react";
import { Col, Form, Row } from "react-bootstrap";
import { MdMail, MdPassword } from "react-icons/md";
import styled from "styled-components";

import { useForm } from "../../../utils";
import { ModalProps } from "../Modal";
import { Modal } from "..";
import styles from "../../../styles/styles";
import { Button, Input, Layout, Spacer, Typography } from "../../base";
import { registerUser } from "../../../api";

export const StyledRow = styled(Row)`
  margin: ${styles.spacing[0]};
`;

export const StyledCol = styled(Col)`
  margin: ${styles.spacing[0]};
  padding: ${styles.spacing[0]};
`;

function RegisterModal(props: ModalProps) {
  const handleSubmit = async () => {
    if (password != confirmPassword) {
      setErrors(["Passwords do not match"]);
      return;
    }

    try {
      await registerUser({
        firstname,
        lastname,
        email,
        password,
      });
    } catch (e) {
      setErrors(["Something went wrong"]);
    }

    props.setShow(false);
  };

  const {
    onChange,
    onSubmit,
    values: { firstname, lastname, email, password, confirmPassword },
    setErrors,
  } = useForm(handleSubmit, {
    firstname: "",
    lastname: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  return (
    <Modal {...props}>
      <Typography variant="h5">create an account</Typography>
      <Spacer height={24} />
      <Form onSubmit={onSubmit}>
        <Layout>
          <Input
            name="firstname"
            placeholder="first name"
            value={firstname}
            onChange={onChange}
          />

          <Spacer width={8} />

          <Input
            name="lastname"
            placeholder="last name"
            value={lastname}
            onChange={onChange}
          />
        </Layout>
        <Spacer height={8} />
        <Input
          name="email"
          value={email}
          onChange={onChange}
          icon={MdMail}
          placeholder="email"
        />
        <Spacer height={8} />
        <Input
          name="password"
          value={password}
          onChange={onChange}
          icon={MdPassword}
          placeholder="password"
        />
        <Spacer height={8} />
        <Input
          name="confirmPassword"
          value={confirmPassword}
          onChange={onChange}
          icon={MdPassword}
          placeholder="re-type password"
        />
        <Button primary type="submit">
          submit
        </Button>
      </Form>
    </Modal>
  );
}

export default RegisterModal;
