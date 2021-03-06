import React, { useContext } from "react";
import { Form } from "react-bootstrap";
import { MdMail, MdPassword } from "react-icons/md";
import { Modal } from "..";
import { Button, Spacer, Typography } from "../../base";
import { Input } from "../../base";
import { useForm } from "../../../utils";
import { AuthContext, UserData } from "../../../contexts/AuthContextProvider";
import { ModalProps } from "../Modal";
import { loginUser } from "../../../api";
import ErrorMessage from "../../ErrorMessage";

function LoginModal(props: ModalProps) {
  const { login } = useContext(AuthContext);
  const handleSubmit = async () => {
    try {
      await loginUser({
        email,
        password,
      }).then((userData: UserData) => login(userData));
    } catch (e: any) {
      setError(e.response?.data?.message ?? "Something went wrong.");
    }
    props.setShow(false);
  };

  const {
    onChange,
    onSubmit,
    values: { email, password },
    error,
    setError,
  } = useForm(handleSubmit, {
    email: "",
    lastname: "",
  });

  return (
    <Modal
      {...props}
      modalCTA={
        <Button primary type="submit">
          submit
        </Button>
      }
    >
      <Typography variant="h5">login</Typography>
      <Spacer height={24} />
      <Form onSubmit={onSubmit}>
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
      </Form>
      <ErrorMessage>{error}</ErrorMessage>
    </Modal>
  );
}

export default LoginModal;
